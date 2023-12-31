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
@Sql(scripts = "/place-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithCustomMockUser
    public void should_latitude_is_returned_in_the_decimal_type_When_getting_place_list() throws Exception {
        // 장소 조회시 위도 latitude가 decimal 타입으로 반환되는 지 확인한다.
        mockMvc.perform(get("/v1/scraps/places")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].title")
                        .value("서울 빌딩"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].latitude")
                        .value(37.00000000000000));
    }

    @Test
    @WithCustomMockUser
    public void should_memo_that_is_not_deleted_is_gotten_and_deleted_memo_is_not_gotten_When_getting_place_lists() throws Exception {
        //장소 스크랩 리스트 조회할 때, 삭제되지 않은 메모는 조회되고, 삭제된 메모는 조회된다.
        mockMvc.perform(get("/v1/scraps/places")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].memoList[0].memoText").value("Hello 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].memoList[1]").doesNotExist());
    }
}
