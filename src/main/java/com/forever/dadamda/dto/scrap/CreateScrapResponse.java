package com.forever.dadamda.dto.scrap;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateScrapResponse {

    private String pageUrl;

    public static CreateScrapResponse of(String pageUrl) {
        return new CreateScrapResponse(pageUrl);
    }
}