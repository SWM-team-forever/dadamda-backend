package com.forever.dadamda.controller.scrap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
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
public class ScrapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithCustomMockUser
    public void should_Scrap_When_KeywordExistSInTitle() throws Exception {
        mockMvc.perform(get("/v1/scraps/search")
                        .param("keyword", "맥북")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("Coupang의 맥북 상품"));
    }

    @Test
    @WithCustomMockUser
    public void should_Scrap_When_KeywordExistSInDescription() throws Exception {
        mockMvc.perform(get("/v1/scraps/search")
                        .param("keyword", "16인치")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("Coupang의 맥북 상품"));
    }

    @Test
    @WithCustomMockUser
    public void should_return_400_error_When_no_keyword_is_entered() throws Exception {
        // 검색시 입력한 keyword가 없는 경우 에러 발생
        mockMvc.perform(get("/v1/scraps/search")
                        .param("keyword", " ")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithCustomMockUser
    public void should_return_no_scrap_When_searching_keywords_for_deleted_scrap() throws Exception {
        // 삭제된 스크랩의 키워드 검색할 때 검색되지 않음
        mockMvc.perform(get("/v1/scraps/search")
                        .param("keyword", "내일")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0]").doesNotExist());
    }

    @Test
    @WithCustomMockUser
    public void should_the_title_of_the_1st_note_is_Hello_When_the_notes_are_sorted_in_ascending_order_based_on_their_created_date() throws Exception {
        // 메모의 created date가 생성된 날짜 오래된 순서대로 정렬될 때, 1번째 메모의 title이 "Hello"라면 테스트 통과
        mockMvc.perform(get("/v1/scraps")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].memoList[0].memoText").value("Hello 1"));
    }

    @Test
    @WithCustomMockUser
    public void should_the_created_date_of_the_1st_note_is_2023_01_01_When_the_notes_are_sorted_in_ascending_order_based_on_their_created_date() throws Exception {
        // 메모의 created date가 생성된 날짜 오래된 순서대로 정렬될 때, 1번째 메모의 created_date가 2023-01-01이라면 테스트 통과
        mockMvc.perform(get("/v1/scraps")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].memoList[0].createdDate").value("2023-01-01T11:11:01"));
    }

    @Test
    @WithCustomMockUser
    public void should_the_scrap_is_modified_successfully_When_modifying_the_scrap_by_the_scrap_id_is_positive_and_dtype_is_not_null() throws Exception {
        // 스크랩 id가 양수이고 dtype이 null이 아닌 스크랩 수정할 때, 성공적으로 스크랩이 수정된다.
        //given
        UpdateScrapRequest updateScrapRequest = UpdateScrapRequest
                .builder()
                .scrapId(1L)
                .dType("product")
                .description("설명")
                .build();
        String content = objectMapper.writeValueAsString(updateScrapRequest);

        // when
        // then
        mockMvc.perform(patch("/v1/scraps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithCustomMockUser
    public void should_4xx_error_When_modifying_the_scrap_by_being_more_than_siteName_100_characters() throws Exception {
        // siteName이 100자 초과일 때, 400 에러 발생
        //given
        UpdateScrapRequest updateScrapRequest = UpdateScrapRequest
                .builder()
                .scrapId(1L)
                .dType("product")
                .siteName("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111")
                .build();
        String content = objectMapper.writeValueAsString(updateScrapRequest);

        // when
        // then
        mockMvc.perform(patch("/v1/scraps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
