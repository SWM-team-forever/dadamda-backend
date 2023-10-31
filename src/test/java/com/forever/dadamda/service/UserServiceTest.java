package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/user-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    String existentEmail = "1234@naver.com";

    @Test
    void should_the_changed_nickname_is_the_same_as_the_updateNickname_and_modified_date_is_updated_when_the_nickname_is_changed() {
        // 닉네임을 변경할 때, 변경된 닉네임이 주어진 닉네임과 동일하고 modified_date가 업데이트된다.
        // given
        String updateNickname = "nickname1";

        //when
        userService.updateNickname(updateNickname, existentEmail);

        //then
        User user = userRepository.findByEmailAndDeletedDateIsNull(existentEmail).get();
        assertThat(user.getNickname()).isEqualTo(updateNickname);
        assertThat(user.getModifiedDate()).isAfter(user.getCreatedDate());
    }
}
