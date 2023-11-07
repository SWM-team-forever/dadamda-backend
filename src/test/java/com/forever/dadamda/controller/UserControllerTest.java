package com.forever.dadamda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.dadamda.dto.user.UpdateNicknameRequest;
import com.forever.dadamda.mock.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/user-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithCustomMockUser
    public void should_it_returns_OK_When_changing_the_nickname() throws Exception {
        // 닉네임을 변경할 때, 성공적으로 변경된다.
        //given
        UpdateNicknameRequest updateNicknameRequest = UpdateNicknameRequest.builder()
                .nickname("test")
                .build();
        String content = objectMapper.writeValueAsString(updateNicknameRequest);

        //when
        //then
        mockMvc.perform(patch("/v1/user/profile/nickname")
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithCustomMockUser
    public void should_it_returns_4xx_errors_and_resultCode_is_BR003_When_changing_the_nickname_that_already_exists()
            throws Exception {
        //given
        UpdateNicknameRequest updateNicknameRequest = UpdateNicknameRequest.builder()
                .nickname("귀여운해달2")
                .build();
        String content = objectMapper.writeValueAsString(updateNicknameRequest);

        //when
        //then
        mockMvc.perform(patch("/v1/user/profile/nickname")
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value("BR003"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("이미 사용중인 닉네임입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    @WithCustomMockUser
    public void should_it_returns_name_email_provider_nickname_and_createdAt_of_unix_type_is_BR003_When_getting_user()
            throws Exception {
        // 유저를 조회할 때, 이름, 이메일, 프로바이더, 프로필 URL, 닉네임, 생성일자를 조회할 수 있다.
        mockMvc.perform(get("/v1/user")
                        .header("X-AUTH-TOKEN", "aaaaaaa")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("koko"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("1234@naver.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.provider").value("GOOGLE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickname").value("귀여운해달1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdAt").value("1672744261"));
    }
}
