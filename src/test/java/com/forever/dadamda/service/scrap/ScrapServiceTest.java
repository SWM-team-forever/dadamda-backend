package com.forever.dadamda.service.scrap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.forever.dadamda.dto.scrap.GetScrapResponse;
import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.MemoRepository;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.repository.scrap.ScrapRepository;
import com.forever.dadamda.service.WebClientService;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/scrap-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ScrapServiceTest {

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private WebClientService webClientService;

    @Autowired
    private MemoRepository memoRepository;

    String email = "1234@naver.com";
    Long existentScrapId = 1L;
    Long notExistentScrapId = 100L;

    String pageUrl = "https://www.naver.com";

    int scrapCountExpected = 2;

    @Test
    void should_return_success_When_existent_member_deletes_one_scrap() {
        //given
        //when
        scrapService.deleteScraps(email, existentScrapId);

        //then
        assertThat(scrapRepository.findById(existentScrapId).get().getDeletedDate()).isNotNull();
    }

    @Test
    void should_return_not_found_exception_When_existent_member_deletes_not_existent_scrap() {
        //given
        //when
        //then
        assertThatThrownBy(() -> scrapService.deleteScraps(email, notExistentScrapId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_return_not_found_exception_When_not_existent_member_deletes_scrap() {
        //given
        //when
        //then
        assertThatThrownBy(() -> scrapService.deleteScraps(email, notExistentScrapId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_return_two_notes_When_write_two_notes_on_the_first_scrap() {
        // 첫번째 스크랩에 메모를 2개 작성 했을 때,작성한 메모가 모두 조회되는지 확인
        //given
        //when
        Slice<GetScrapResponse> getScrapResponses = scrapService.getScraps(email,
                PageRequest.of(0, 10));
        int memoCount = getScrapResponses.getContent().get(0).getMemoList().size();

        //then
        assertEquals(memoCount, scrapCountExpected);
    }

    @Test
    void should_other_type_of_scrap_is_saved_When_webClientService_crawlingItem_returns_null() throws ParseException {
        // webClientService.crawlingItem()이 null을 반환할 때, Other 타입의 Scrap이 저장되는지 확인
        //given
        memoRepository.deleteAll();
        scrapRepository.deleteAll();

        BDDMockito.when(webClientService.crawlingItem("http://localhost:123", pageUrl))
                .thenReturn(null);

        User user = userRepository.findById(1L).get();

        //when
        //then
        assertThat(scrapService.saveScraps(user, pageUrl)).isInstanceOf(Other.class);
        assertThat(scrapRepository.findByPageUrlAndUserAndDeletedDateIsNull(pageUrl, user)
                .isPresent()).isTrue();
    }
}
