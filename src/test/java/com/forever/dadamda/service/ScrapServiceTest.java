package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.Provider;
import com.forever.dadamda.entity.user.Role;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.ScrapRepository;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.service.scrap.ScrapService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
}