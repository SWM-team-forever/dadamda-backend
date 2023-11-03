package com.forever.dadamda.controller;


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
}
