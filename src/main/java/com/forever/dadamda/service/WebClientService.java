package com.forever.dadamda.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import com.forever.dadamda.dto.webClient.WebClientResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {

    @Transactional
    public WebClientBodyResponse crawlingItem(String crawlingApiEndPoint, String pageUrl) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("url", pageUrl);

        WebClient webClient = WebClient.builder().baseUrl(crawlingApiEndPoint).build();

        WebClientResponse webClientResponse = webClient.post()
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(WebClientResponse.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        WebClientBodyResponse webClientBodyResponse;
        try {
            if (webClientResponse == null) {
                throw new RuntimeException("webClientResponse is null");
            }
            webClientBodyResponse = objectMapper.readValue(
                    webClientResponse.getBody(),
                    WebClientBodyResponse.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return webClientBodyResponse;
    }
}
