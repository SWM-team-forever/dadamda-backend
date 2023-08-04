package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.Provider;
import com.forever.dadamda.entity.user.Role;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.ScrapRepository;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.service.scrap.ScrapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScrapServiceTest {

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
     void setUp() {
        User user = User.builder().name("123").email("123@naver.com")
                .profileUrl("https://www.naver.com").provider(Provider.NAVER).role(Role.USER)
                .build();
        userRepository.save(user);
        Scrap scrap = new Scrap(user, "https://www.naver.com");
        scrapRepository.save(scrap);
    }

    @Test
    void delete_scrap() {
        //given
        //when
        scrapService.deleteScraps("123@naver.com", 1L);

        //then
        assertThat(scrapRepository.findById(1L).get().getDeletedDate()).isNotNull();
    }
}
