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
@Sql(scripts = "/setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithCustomMockUser
    public void should_only_article_is_searched_When_searching_for_keywords_that_are_also_in_other_scraps() throws Exception {
        //다른 스크랩에도 있는 키워드 검색시, 아티클 스크랩만 검색되는지 확인
        mockMvc.perform(get("/v1/scraps/articles/search")
                        .param("keyword", "오늘")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("오늘의 일기 3"));
    }

    @Test
    @WithCustomMockUser
    public void should_memo_that_is_not_deleted_is_gotten_and_deleted_memo_is_not_gotten_When_getting_article_lists() throws Exception {
        //아티클 스크랩 리스트 조회할 때, 삭제되지 않은 메모는 조회되고, 삭제된 메모는 조회되지 않는지 확인
        mockMvc.perform(get("/v1/scraps/articles")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].memoList[0].memoText").value("Hello 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].memoList[1]").doesNotExist());
    }
}
