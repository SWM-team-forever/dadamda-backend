package com.forever.dadamda.controller;

import com.forever.dadamda.mock.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "classpath:setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithCustomMockUser
    public void IfUserExistsThenGetUserInfoReturnsSuccess() throws Exception {
        mockMvc.perform(get("/v1/user")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
