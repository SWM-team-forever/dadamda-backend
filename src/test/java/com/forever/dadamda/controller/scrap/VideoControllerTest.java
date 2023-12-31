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
public class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithCustomMockUser
    public void should_videos_are_sorted_in_created_date_When_searching_for_multiple_scraps() throws Exception {
        //여러 개의 스크랩 검색시, 최신 순서 대로 정렬 되었는 지 확인
        mockMvc.perform(get("/v1/scraps/videos/search")
                        .param("keyword", "오늘")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("오늘의 일기 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].title").value("오늘의 일기 1"));
    }

    @Test
    @WithCustomMockUser
    public void should_an_empty_array_is_returned_When_keyword_is_not_present() throws Exception {
        //스크랩 검색할 때, 해당 키워드가 없을 때 빈 배열이 반환되는 지 확인
        mockMvc.perform(get("/v1/scraps/videos/search")
                        .param("keyword", "내일")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").doesNotExist());
    }

    @Test
    @WithCustomMockUser
    public void should_memo_that_is_not_deleted_is_gotten_and_deleted_memo_is_not_gotten_When_getting_video_lists() throws Exception {
        //영상 스크랩 리스트 조회할 때, 삭제되지 않은 메모는 조회되고, 삭제된 메모는 조회된다.
        mockMvc.perform(get("/v1/scraps/videos")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].memoList[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].memoList[1]").doesNotExist());
    }
}
