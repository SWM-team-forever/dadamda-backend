package com.forever.dadamda.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.forever.dadamda.mock.WithCustomMockUser;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/board-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class TrendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    UUID board1UUID = UUID.fromString("30373832-6566-3438-2d61-3433392d3131");

    UUID board2UUID = UUID.fromString("30373832-6566-3438-2d61-3433392d3132");

    UUID board3UUID = UUID.fromString("30373832-6566-3438-2d61-3433392d3133");

    @Test
    @WithCustomMockUser
    public void should_it_returns_4xx_error_When_heart_are_pressed_on_a_board_that_is_not_published_in_trending()
            throws Exception {
        // 트렌딩에 게시되지 않은 보드의 하트 누를 때, 4xx 에러를 반환하는지 확인
        mockMvc.perform(post("/v1/trends/heart/{boardUUID}", board2UUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value("NF005"));
    }

    @Test
    @WithCustomMockUser
    public void should_it_returns_ok_When_heart_are_pressed_on_a_board_that_is_published_in_trending()
            throws Exception {
        // 트랜딩에 게시된 보드를 하트 누를 때, OK를 반환하는지 확인
        mockMvc.perform(post("/v1/trends/heart/{boardUUID}", board1UUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.isHeart").value(true));
    }

    @Test
    public void should_it_is_arranged_in_the_order_of_heart_share_view_When_getting_trending_boards()
            throws Exception {
        // 트랜딩 보드를 조회할 때, 하트, 공유, 조회 순서대로 정렬된다.
        mockMvc.perform(get("/ov1/trends/boards")
                        .param("startDate", "2023-01-01 00:00:00")
                        .param("endDate", "2023-01-31 23:59:59")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("board3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].title").value("board1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].title").value("board4"));
    }

    @Test
    public void should_content_is_empty_if_there_is_no_board_created_within_the_date_When_getting_trending_boards()
            throws Exception {
        // 트랜딩 보드를 조회할 때, 날짜 안에 생성된 보드가 없으면 content가 비어있다.
        mockMvc.perform(get("/ov1/trends/boards")
                        .param("startDate", "2022-02-01 00:00:00")
                        .param("endDate", "2022-02-28 23:59:59")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isEmpty());
    }

    @Test
    public void should_it_returns_the_boards_When_getting_trending_boards_by_tags()
            throws Exception {
        // 태그로 트랜딩 보드를 조회할 때, 보드를 조회할 수 있다.
        mockMvc.perform(get("/ov1/trends/boards")
                        .param("startDate", "2023-01-01 00:00:00")
                        .param("endDate", "2023-01-31 23:59:59")
                        .param("page", "0")
                        .param("size", "10")
                        .param("tag", "ENTERTAINMENT_ART")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("board1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].title").doesNotExist());
    }

    @Test
    public void should_it_returns_user_nickname_When_getting_popular_users()
            throws Exception {
        // 인기 유저를 조회할 때, 유저의 닉네임을 조회할 수 있다.
        mockMvc.perform(get("/ov1/trends/popularUsers")
                        .param("startDate", "2023-01-01 00:00:00")
                        .param("endDate", "2023-01-31 23:59:59")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].nickname").value("귀여운해달1"));
    }

    @Test
    public void should_the_number_of_hearts_is_arranged_in_the_order_of_large_numbers_When_getting_popular_users()
            throws Exception {
        // 인기 유저를 조회할 때, 하트의 개수가 많은 순서대로 정렬된다.
        mockMvc.perform(get("/ov1/trends/popularUsers")
                        .param("startDate", "2023-01-01 00:00:00")
                        .param("endDate", "2023-02-20 23:59:59")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].nickname").value("귀여운해달2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].nickname").value("귀여운해달1"));
    }

    @Test
    public void should_the_number_of_boards_is_arranged_in_the_order_of_large_number_if_the_number_of_hearts_is_the_same_When_getting_popular_users()
            throws Exception {
        // 인기 유저를 조회할 때, 하트의 개수가 동일한 경우 보드의 개수가 많은 순서대로 정렬된다.
        mockMvc.perform(get("/ov1/trends/popularUsers")
                        .param("startDate", "2023-01-01 00:00:00")
                        .param("endDate", "2023-03-31 23:59:59")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].nickname").value("귀여운해달3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].nickname").value("귀여운해달2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].nickname").value("귀여운해달1"));
    }

    @Test
    public void should_the_content_is_empty_if_there_is_no_corresponding_board_When_searching_for_trending_board_name()
            throws Exception {
        // 트렌딩 보드명 검색할 때, 해당하는 보드가 없는 경우 content가 비어있다.
        mockMvc.perform(get("/ov1/trends/search")
                        .param("startDate", "2023-01-01 00:00:00")
                        .param("endDate", "2023-03-31 23:59:59")
                        .param("keyword", "test")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isEmpty());
    }

    @Test
    public void should_the_content_is_empty_if_you_search_for_an_unpublished_board_When_searching_for_trending_board_name()
            throws Exception {
        // 트렌딩 보드명 검색할 때, 게시되지 않은 보드를 검색하면 content가 비어있다.
        mockMvc.perform(get("/ov1/trends/search")
                        .param("startDate", "2023-01-01 00:00:00")
                        .param("endDate", "2023-01-31 23:59:59")
                        .param("keyword", "board2")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isEmpty());
    }

    @Test
    public void should_it_is_arranged_in_the_order_of_heart_share_view_and_deleted_boards_are_not_searched_When_searching_for_trending_board_name()
            throws Exception {
        // 트렌딩 보드명 검색할 때, 하트 순, 공유 순, 조회 순으로 정렬되고, 삭제된 보드는 검색되지 않는다.
        mockMvc.perform(get("/ov1/trends/search")
                        .param("startDate", "2023-01-01 00:00:00")
                        .param("endDate", "2023-01-31 23:59:59")
                        .param("keyword", "board")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("board3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].title").value("board1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].title").value("board4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].title").doesNotExist());
    }
}
