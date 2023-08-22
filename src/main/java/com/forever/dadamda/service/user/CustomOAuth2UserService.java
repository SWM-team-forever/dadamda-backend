package com.forever.dadamda.service.user;

import com.forever.dadamda.dto.user.OAuthAttributes;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.UserRepository;
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

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        log.info("loadUser userRequest: {}", userRequest);
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        log.info("loadUser oAuth2User: {}", oAuth2User.getAttributes());
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("loadUser registrationId: {}", registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        log.info("loadUser userNameAttributeName: {}", userNameAttributeName);

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,
                userNameAttributeName, oAuth2User.getAttributes());
        log.info("loadUser attributes: {}", attributes.getAttributes());

        User user = saveOrUpdate(attributes);
        log.info("loadUser user: {}", user);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {

        User user = userRepository.findByEmailAndDeletedDateIsNull(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getProfileUrl()))
                .orElse(attributes.toEntity());
        log.info("saveOrUpdate user: {}", user);
        return userRepository.save(user);
    }
}
