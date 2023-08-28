package com.forever.dadamda.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.user.Provider;
import com.forever.dadamda.entity.user.Role;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class ScrapRepositoryTest {
    @Autowired
    private UserRepository userRepository;

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
    }

    @Test
    void IfUserExistsThenFindByEmailAndDeletedDateIsNullReturnsSuccess() {
        // given
        //when
        Optional<User> result = userRepository.findByEmailAndDeletedDateIsNull("1234@naver.com");

        // then
        assertThat(result.isPresent()).isTrue();
    }
}
