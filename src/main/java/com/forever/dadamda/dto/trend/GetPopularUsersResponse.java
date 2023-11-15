package com.forever.dadamda.dto.trend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetPopularUsersResponse {
    private String profileUrl;
    private String nickname;

    public static GetPopularUsersResponse of(String profileUrl, String nickname) {
        return GetPopularUsersResponse.builder()
                .profileUrl(profileUrl)
                .nickname(nickname)
                .build();
    }
}
