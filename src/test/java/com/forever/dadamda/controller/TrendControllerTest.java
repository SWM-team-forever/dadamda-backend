package com.forever.dadamda.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    @WithCustomMockUser
    public void should_it_returns_4xx_error_and_BR004_When_heart_is_canceled_on_a_board_that_is_not_pressed_on_hearts()
            throws Exception {
        // 트랜딩에 게시된 보드를 누르지 않은 하트를 취소할 때, 4xx 에러, BR004를 반환하는지 확인
        mockMvc.perform(delete("/v1/trends/heart/{boardUUID}", board3UUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value("BR004"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("좋아요를 누르지 않은 글입니다."));
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
                        .param("startDate", "2023-02-01 00:00:00")
                        .param("endDate", "2023-02-28 23:59:59")
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
}
