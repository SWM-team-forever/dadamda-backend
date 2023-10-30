package com.forever.dadamda.dto.user;

import com.forever.dadamda.entity.user.Provider;
import com.forever.dadamda.entity.user.Role;
import com.forever.dadamda.entity.user.User;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String profileUrl;
    private Provider provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
            String nameAttributeKey,
            String name, String email, String profileUrl, Provider provider) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

    public static OAuthAttributes of(String registrationId,
            String userNameAttributeName,
            Map<String, Object> attributes) {

        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
            Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(Provider.GOOGLE)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .profileUrl((String) kakaoProfile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(Provider.KAKAO)
                .build();
    }

    public User toEntity(String nickname, UUID uuid) {
        return User.builder()
                .name(name)
                .email(email)
                .profileUrl(profileUrl)
                .provider(provider)
                .role(Role.USER)
                .uuid(uuid)
                .nickname(nickname)
                .build();
    }
}
