package com.forever.dadamda.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.dadamda.dto.memo.DeleteMemoRequest;
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
public class MemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithCustomMockUser
    public void should_it_is_deleted_normally_When_deleting_an_existing_memo()
            throws Exception {
        //존재하는 메모 삭제할 때, 정상적으로 삭제된다.
        //given
        DeleteMemoRequest deleteMemoRequest = DeleteMemoRequest.builder()
                .memoId(1L)
                .scrapId(1L)
                .build();
        String content = objectMapper.writeValueAsString(deleteMemoRequest);

        //when
        //then
        mockMvc.perform(delete("/v1/scraps/memo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithCustomMockUser
    public void should_it_return_400_error_When_deleting_notes_without_memoId()
            throws Exception {
        //memoId를 입력하지 않는 경우, 400 에러를 반환하는지 확인
        //given
        DeleteMemoRequest deleteMemoRequest = DeleteMemoRequest.builder()
                .scrapId(1L)
                .build();
        String content = objectMapper.writeValueAsString(deleteMemoRequest);

        //when
        //then
        mockMvc.perform(delete("/v1/scraps/memo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
