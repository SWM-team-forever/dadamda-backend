package com.forever.dadamda.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

    private String pageUrl;

    public static LoginResponse of(String pageUrl) {
        return new LoginResponse(pageUrl);
    }
}