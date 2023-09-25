package com.forever.dadamda.repository.scrap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.TestConfig;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.repository.scrap.other.OtherRepository;
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
public class OtherRepositoryTest {

    @Autowired
    private OtherRepository otherRepository;

    @Autowired
    private UserRepository userRepository;

    String email = "1234@naver.com";

    @Test
    void should_the_scrap_is_not_returned_when_searching_for_other_category_in_which_the_scrap_does_not_exist() {
        // 스크랩이 존재하지 않을 때 검색하면, 스크랩이 반환되지 않는다.
        // given
        User user = userRepository.findByEmailAndDeletedDateIsNull(email).get();
        String keyword = "오늘";
        Pageable pageable = PageRequest.of(0, 2);

        //when
        Slice<Other> results = otherRepository.searchKeywordInOtherOrderByCreatedDateDesc(user,
                keyword, pageable);

        // then
        assertThat(results.getContent().size()).isEqualTo(0);
    }
}
