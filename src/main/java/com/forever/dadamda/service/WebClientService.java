package com.forever.dadamda.service;

import java.util.HashMap;
import java.util.Map;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {

    @Value("${crawling.server.post.api.endPoint}")
    private String crawlingApiEndPoint;

    @Transactional
    public JSONObject crawlingItem(String pageUrl) throws ParseException {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("url", pageUrl);
        WebClient webClient = WebClient.builder().baseUrl(crawlingApiEndPoint).build();

        Map<String, Object> response = webClient.post()
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        JSONParser jsonParser = new JSONParser();

        Object obj = jsonParser.parse(response.get("body").toString());

        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject;
    }
}
