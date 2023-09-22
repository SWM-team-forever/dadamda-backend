package com.forever.dadamda.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.scrap.ScrapRepository;
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
public class ScrapRepositoryTest {

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private UserRepository userRepository;

    String email = "1234@naver.com";

    @Test
    void should_has_next_is_returned_when_the_next_page_is_present() {
        // 다음 페이지가 있을 때, hasNext가 true로 반환된다.
        // given
        User user = userRepository.findByEmailAndDeletedDateIsNull(email).get();
        String keyword = "오늘";
        Pageable pageable = PageRequest.of(0, 2);

        //when
        Slice<Scrap> results = scrapRepository.searchKeywordInScrapOrderByCreatedDateDesc(user,
                keyword, pageable);

        // then
        assertThat(results.getSize()).isEqualByComparingTo(2);
        assertThat(results.hasNext()).isTrue();
    }
}
