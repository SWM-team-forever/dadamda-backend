package com.forever.dadamda.dto.user;

import com.forever.dadamda.entity.user.Provider;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserInfoResponse {

    private String name;
    private String email;
    private Provider provider;
    private String profileUrl;
    private String nickname;
    private Long createdAt;
}
