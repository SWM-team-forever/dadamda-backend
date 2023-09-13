package com.forever.dadamda.dto.webClient;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WebClientResponse {

    private String statusCode;
    private WebClientHeaderResponse header;
    private String body;
}

