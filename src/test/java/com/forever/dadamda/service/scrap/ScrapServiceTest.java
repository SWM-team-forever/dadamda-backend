package com.forever.dadamda.service.scrap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.ScrapRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "classpath:setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
public class ScrapServiceTest {

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private ScrapRepository scrapRepository;

    String email = "1234@naver.com";
    Long existentScrapId = 1L;
    Long notExistentScrapId = 100L;

    @Test
    void IfExistentMemberDeletesOneScrapThenReturnSuccess() {
        //given
        //when
        scrapService.deleteScraps(email, existentScrapId);

        //then
        assertThat(scrapRepository.findById(existentScrapId).get().getDeletedDate()).isNotNull();
    }

    @Test
    void IfExistentMemberDeletesNonExistentScrapThenReturnNotFoundException() {
        //given
        //when
        //then
        assertThatThrownBy(() -> scrapService.deleteScraps(email, notExistentScrapId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void IfNonExistentMemberDeletesScrapThenReturnNotFoundException() {
        //given
        //when
        //then
        assertThatThrownBy(() -> scrapService.deleteScraps(email, notExistentScrapId))
                .isInstanceOf(NotFoundException.class);
    }
}
