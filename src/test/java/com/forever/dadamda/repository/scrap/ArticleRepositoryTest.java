package com.forever.dadamda.repository.scrap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.TestConfig;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.repository.scrap.article.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Import(TestConfig.class)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    String email = "1234@naver.com";

    @Test
    void should_the_title_is_returned_when_searching_for_keywords_without_case_insensitive() {
        // 대소문자를 구분하지 않고 keyword를 검색할 때, 검색 결과가 있으면 해당 title을 반환된다.
        //스크랩 검색할 때, 대소문자를 구분하지 않고 검색할 수 있는 지 확인
        // given
        User user = userRepository.findByEmailAndDeletedDateIsNull(email).get();
        String keyword = "toDay";
        Pageable pageable = PageRequest.of(0, 2);

        //when
        Slice<Article> results = articleRepository.searchKeywordInArticleOrderByCreatedDateDesc(user,
                keyword, pageable);

        // then
        assertThat(results.getContent().get(0).getDescription()).isEqualTo("Today is rainy");
    }
}
