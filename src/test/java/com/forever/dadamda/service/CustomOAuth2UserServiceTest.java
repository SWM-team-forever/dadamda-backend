package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.config.oauth.CustomOAuth2UserService;
import com.forever.dadamda.dto.user.OAuthAttributes;
import com.forever.dadamda.entity.user.Provider;
import com.forever.dadamda.entity.user.Role;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class CustomOAuth2UserServiceTest {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private UserRepository userRepository;

    String email = "1234@gmail.com";

    @Test
    void should_a_new_user_with_new_nickname_is_created_When_no_user_sings_up_with_that_email() {
        // 해당 이메일로 가입한 유저가 없을 때, 닉네임이 생성된 새로운 유저가 생성된다.
        //given
        OAuthAttributes attributes = OAuthAttributes.builder().name("test").email(email)
                .provider(Provider.GOOGLE).build();

        //when
        customOAuth2UserService.saveOrUpdate(attributes);

        //then
        User user = userRepository.findByEmailAndDeletedDateIsNull(email).get();
        assertThat(user).isNotNull();
        assertThat(user.getNickname()).isNotNull();
        assertThat(user.getModifiedDate().toString()).isEqualTo(user.getCreatedDate().toString());
    }

    @Test
    void should_the_existing_nickname_and_uuid_is_not_changed_but_only_the_name_is_changed_When_logging_in_by_changing_the_user_name() {
        //  유저의 이름을 변경하여 로그인할때, 기존의 닉네임은 변경되지 않고, 이름만 변경된다.
        //given
        UUID uuid = UUIDService.generateUUID();
        User savedUser = User.builder().name("test").email(email).nickname("test")
                .provider(Provider.GOOGLE).role(Role.USER).uuid(uuid).build();
        userRepository.save(savedUser);

        OAuthAttributes attributes = OAuthAttributes.builder().name("changedName")
                .email(email).provider(Provider.GOOGLE).build();

        customOAuth2UserService.saveOrUpdate(attributes);

        //then
        User user = userRepository.findByEmailAndDeletedDateIsNull(email).get();
        assertThat(user).isNotNull();
        assertThat(user.getNickname()).isEqualTo("test");
        assertThat(user.getName()).isEqualTo("changedName");
        assertThat(user.getModifiedDate()).isAfter(user.getCreatedDate());
        assertThat(user.getUuid()).isEqualTo(uuid);
    }
}
