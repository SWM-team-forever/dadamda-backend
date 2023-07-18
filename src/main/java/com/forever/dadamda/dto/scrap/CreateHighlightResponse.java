package com.forever.dadamda.dto.scrap;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateHighlightResponse {
    private String pageUrl;

    public static CreateHighlightResponse of(String pageUrl) {
        return new CreateHighlightResponse(pageUrl);
    }
}
