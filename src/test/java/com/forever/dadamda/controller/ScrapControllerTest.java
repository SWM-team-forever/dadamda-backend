package com.forever.dadamda.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.Provider;
import com.forever.dadamda.entity.user.Role;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.mock.WithCustomMockUser;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.repository.scrap.ScrapRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class ScrapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScrapRepository scrapRepository;

    @BeforeEach
     void setUp() {
        User user = User.builder()
                .name("koko")
                .provider(Provider.GOOGLE)
                .role(Role.USER)
                .email("1234@naver.com")
                .profileUrl("https://www.naver.com")
                .build();

        userRepository.save(user);

        Scrap scrap = new Scrap(user, "https://www.naver.com", "네이버의 초기 화면입니다.",
                "thumbnail", "대한민국의 검색 포털 사이트입니다.", "naver");
        scrapRepository.save(scrap);
    }

    @AfterEach
    void clear() {
        scrapRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithCustomMockUser
    public void should_Scrap_When_KeywordExistSInTitle() throws Exception {
        mockMvc.perform(get("/v1/scraps/search")
                        .param("keyword", "네이버")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("네이버의 초기 화면입니다."));
    }

    @Test
    @WithCustomMockUser
    public void should_Scrap_When_KeywordExistSInDescription() throws Exception {
        mockMvc.perform(get("/v1/scraps/search")
                        .param("keyword", "검색")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].title").value("네이버의 초기 화면입니다."));
    }
}
