package com.forever.dadamda.config.oauth;

import static com.forever.dadamda.service.RandomService.adjectivesLength;
import static com.forever.dadamda.service.RandomService.animalsLength;
import static com.forever.dadamda.service.RandomService.generateRandomNickname;
import static com.forever.dadamda.service.RandomService.numberLength;
import static com.forever.dadamda.service.UUIDService.generateUUID;

import com.forever.dadamda.dto.user.OAuthAttributes;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,
                userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    @Transactional
    public User saveOrUpdate(OAuthAttributes attributes) {

        User user = userRepository.findByEmailAndDeletedDateIsNull(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName()))
                .orElseGet(() -> attributes.toEntity(getNewNickname(), generateUUID()));

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String getNewNickname() {
        int limit = adjectivesLength * animalsLength * numberLength;
        Set<String> usedNicknames = new HashSet<>();

        while (usedNicknames.size() < limit) {
            String nickname = generateRandomNickname();

            if (!usedNicknames.contains(nickname) && !userRepository.existsByNickname(nickname)) {
                return nickname;
            }
            usedNicknames.add(nickname);
        }
        throw new RuntimeException("닉네임 생성 실패");
    }
}
