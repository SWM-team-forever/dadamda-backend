package com.forever.dadamda.dto.user;

import com.forever.dadamda.entity.user.Provider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserInfoResponse {

    private String name;
    private String email;
    private Provider provider;
    private String profileUrl;

    public static GetUserInfoResponse of(String name, String email, Provider provider,
            String profileUrl) {
        return new GetUserInfoResponse(name, email, provider, profileUrl);
    }
}
