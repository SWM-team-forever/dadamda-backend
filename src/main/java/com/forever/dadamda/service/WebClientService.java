package com.forever.dadamda.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import com.forever.dadamda.dto.webClient.WebClientResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;

@Service
public class WebClientService {

    @Transactional
    public WebClientBodyResponse crawlingItem(String crawlingApiEndPoint, String pageUrl) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("url", pageUrl);

        WebClient webClient = WebClient.builder().baseUrl(crawlingApiEndPoint).build();

        try {
            WebClientResponse webClientResponse = webClient.post()
                    .bodyValue(bodyMap)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        throw new RuntimeException("4xx");
                    })
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        throw new RuntimeException("5xx");
                    })
                    .bodyToMono(WebClientResponse.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(
                    webClientResponse != null ? webClientResponse.getBody() : null,
                    WebClientBodyResponse.class);


        } catch (Exception e) {
            return null;
        }

    }
}
