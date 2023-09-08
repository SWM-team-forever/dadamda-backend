package com.forever.dadamda.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "classpath:setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    String email = "1234@naver.com";

    @Test
    void IfUserExistsThenFindByEmailAndDeletedDateIsNullReturnsSuccess() {
        // given
        //when
        Optional<User> result = userRepository.findByEmailAndDeletedDateIsNull(email);

        // then
        assertThat(result.isPresent()).isTrue();
    }
}
