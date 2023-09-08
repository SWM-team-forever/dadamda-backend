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
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    String email = "1234@naver.com";

    @Test   //아티클에 해당하지 않는 키워드를 검색했을 때 0개의 아티클을 반환하는지 확인
    void should_Return_zero_article_When_Searching_for_a_keyword_that_not_corresponds_to_article() {
        //given
        //when
        //then
        int searchedArticleNumber = articleService.searchArticles(email, "MAC", PageRequest.of(0, 10)).getNumberOfElements();

        assertEquals( searchedArticleNumber, 0);
    }
}
