package com.forever.dadamda.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.mock.WithCustomMockUser;
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
@Sql(scripts = "/setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithCustomMockUser
    public void should_it_created_normally_When_creating_board_with_the_title_name_and_tag_entered()
            throws Exception {
        //타이틀, 이름, 태그를 입력한 보드를 만들 때, 정상적으로 만들어지는지 확인
        //given
        CreateBoardRequest createBoardRequest = CreateBoardRequest.builder()
                .tag("ENTERTAINMENT_ART")
                .name("board test1")
                .description("board test2")
                .build();
        String content = objectMapper.writeValueAsString(createBoardRequest);

        //when
        //then
        mockMvc.perform(post("/v1/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithCustomMockUser
    public void should_it_returns_4xx_error_When_creating_board_with_a_tag_that_does_not_exist()
            throws Exception {
        //존재하지 않는 태그 입력할 때, 4xx 에러를 반환하는지 확인
        //given
        CreateBoardRequest createBoardRequest = CreateBoardRequest.builder()
                .tag("ENTERTAINMENT_ARTIST")
                .name("test")
                .description("test")
                .build();
        String content = objectMapper.writeValueAsString(createBoardRequest);

        //when
        //then
        mockMvc.perform(post("/v1/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
