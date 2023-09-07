package com.forever.dadamda.service.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "classpath:setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
public class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    String email = "1234@naver.com";

    @Test   //한 개의 상품에 해당하는 키워드를 검색했을 때 1개의 상품을 반환하는지 확인
    void should_Return_two_product_When_Searching_for_a_keyword_that_corresponds_to_two_videos() {
        //given
        //when
        //then
        int searchedProductNumber = videoService.searchVideos(email, "오늘",
                PageRequest.of(0, 10)).getNumberOfElements();

        assertEquals(searchedProductNumber, 2);
    }
}
