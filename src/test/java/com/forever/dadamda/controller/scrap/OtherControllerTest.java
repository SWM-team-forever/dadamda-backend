package com.forever.dadamda.controller.scrap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.forever.dadamda.mock.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/other-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class OtherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithCustomMockUser
    public void should_the_2rd_scrap_is_the_first_one_When_page_is_1_size_is_2_and_there_are_4_scraps() throws Exception {
        //page 1, size 2이고 검색된 기타 스크랩이 4개일 때, 2번째 스크랩이 첫 번째로 나오는지 확인
        mockMvc.perform(get("/v1/scraps/others/search")
                        .param("keyword", "google")
                        .param("page", "1")
                        .param("size", "2")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("Google Main Page 2"));
    }

    @Test
    @WithCustomMockUser
    public void should_memo_that_is_not_deleted_is_gotten_and_deleted_memo_is_not_gotten_When_getting_other_lists() throws Exception {
        //기타 스크랩 리스트 조회할 때, 삭제되지 않은 메모는 조회되고, 삭제된 메모는 조회된다.
        mockMvc.perform(get("/v1/scraps/others")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].memoList[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].memoList[1]").doesNotExist());
    }
}
