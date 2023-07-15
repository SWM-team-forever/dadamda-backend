package com.forever.dadamda.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrapResponse {

    private String pageUrl;

    public static ScrapResponse of(String pageUrl) {
        return new ScrapResponse(pageUrl);
    }
}