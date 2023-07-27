package com.forever.dadamda.dto.user;

import com.forever.dadamda.entity.user.Provider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserInfoResponse {

    private String name;
    private String email;
    private Provider provider;
    private String profileUrl;

    @Builder
    public GetUserInfoResponse(String name, String email, Provider provider,
            String profileUrl) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.profileUrl = profileUrl;
    }
}
