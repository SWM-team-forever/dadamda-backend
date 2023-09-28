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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithCustomMockUser
    public void should_Products_When_Keyword_exists_in_title() throws Exception {
        mockMvc.perform(get("/v1/scraps/products/search")
                        .param("keyword", "coupang")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("Coupang의 맥북 상품"));
    }

    @Test
    @WithCustomMockUser
    public void should_Products_When_Keyword_exists_in_description() throws Exception {
        //영어 대소문자 구분 없이 검색, 일부 검색어만 입력해도 검색 가능
        mockMvc.perform(get("/v1/scraps/products/search")
                        .param("keyword", "MAC")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("Coupang의 맥북 상품"));
    }

    @Test
    @WithCustomMockUser
    public void should_memo_that_is_not_deleted_is_gotten_and_deleted_memo_is_not_gotten_When_getting_product_lists() throws Exception {
        //상품 스크랩 리스트 조회할 때, 삭제되지 않은 메모는 조회되고, 삭제된 메모는 조회된다.
        mockMvc.perform(get("/v1/scraps/products")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].memoList[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].memoList[2]").doesNotExist());
    }
}
