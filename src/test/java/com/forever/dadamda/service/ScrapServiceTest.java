package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.forever.dadamda.dto.scrap.GetScrapResponse;
import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.Provider;
import com.forever.dadamda.entity.user.Role;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.ScrapRepository;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.service.scrap.ScrapService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class ScrapServiceTest {

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clear() {
        scrapRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void IfExistentMemberDeletesOneScrapThenReturnSuccess() {
        //given
        User user = User.builder().name("123").email("123@naver.com")
                .profileUrl("https://www.naver.com").provider(Provider.NAVER).role(Role.USER)
                .build();
        userRepository.save(user);
        Scrap scrap = new Scrap(user, "https://www.naver.com");
        scrapRepository.save(scrap);

        //when
        scrapService.deleteScraps("123@naver.com", 1L);

        //then
        assertThat(scrapRepository.findById(1L).get().getDeletedDate()).isNotNull();
    }

    @Test
    void IfExistentMemberDeletesNonExistentScrapThenReturnNotFoundException() {
        //given
        User user = User.builder().name("123").email("123@naver.com")
                .profileUrl("https://www.naver.com").provider(Provider.NAVER).role(Role.USER)
                .build();
        userRepository.save(user);

        //when
        //then
        assertThatThrownBy(() -> scrapService.deleteScraps("123@naver.com", 10L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void IfNonExistentMemberDeletesScrapThenReturnNotFoundException() {
        //given
        //when
        //then
        assertThatThrownBy(() -> scrapService.deleteScraps("1234@naver.com", 10L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test //searchScraps Test Code
    void should_ThrowNotFoundException_When_KeywordNotExist() {
        //given
        User user = User.builder().name("123").email("123@naver.com")
                .profileUrl("https://www.naver.com").provider(Provider.NAVER).role(Role.USER)
                .build();

        userRepository.save(user);

        Scrap scrap = new Scrap(user, "https://www.naver.com", "네이버",
                "thumbnail", "대한민국의 검색 포털 사이트입니다.", "naver");
        scrapRepository.save(scrap);

        //when
        //then
        assertThatThrownBy(
                () -> scrapService.searchScraps("1234@naver.com", "다음", PageRequest.of(0, 10)))
                .isInstanceOf(NotFoundException.class);
    }
}
