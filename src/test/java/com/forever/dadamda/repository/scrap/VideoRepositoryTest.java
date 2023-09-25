package com.forever.dadamda.repository.scrap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.scrap.Video;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.TestConfig;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.repository.scrap.video.VideoRepository;
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
public class VideoRepositoryTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    String email = "1234@naver.com";

    @Test
    void should_has_next_is_returned_false_when_there_is_no_next_page() {
        // 검색한 결과가 없을 때, hasNext가 false로 반환된다.
        // given
        User user = userRepository.findByEmailAndDeletedDateIsNull(email).get();
        String keyword = "내일";
        Pageable pageable = PageRequest.of(0, 2);

        // when
        Slice<Video> results = videoRepository.searchKeywordInVideoOrderByCreatedDateDesc(user,
                keyword, pageable);

        // then
        assertThat(results.hasNext()).isFalse();
    }

}
