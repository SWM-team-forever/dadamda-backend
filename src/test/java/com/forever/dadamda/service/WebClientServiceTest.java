package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class WebClientServiceTest {

    @Autowired
    private WebClientService webClientService;

    private MockWebServer mockWebServer;
    private String mockWebServerUrl;

    private final String mockScrap = "{\n"
            + "  \"statusCode\": 200,\n"
            + "  \"headers\": {\n"
            + "    \"Content-Type\": \"application/json\"\n"
            + "  },\n"
            + "  \"body\": \"{\\\"type\\\": \\\"place\\\", \\\"page_url\\\": \\\"https://map.kakao.com/1234\\\", "
            + "\\\"site_name\\\": \\\"KakaoMap\\\", \\\"lat\\\": 37.50359439708544, \\\"lng\\\": 127.04484896895218, "
            + "\\\"title\\\": \\\"서울역\\\", \\\"address\\\": \\\"서울특별시 중구 소공동 세종대로18길 2\\\", "
            + "\\\"phonenum\\\": \\\"1522-3232\\\", \\\"zipcode\\\": \\\"06151\\\", "
            + "\\\"homepageUrl\\\": \\\"https://www.seoul.co.kr\\\", \\\"category\\\": \\\"지하철\\\"}\"\n"
            + "}\n";

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        mockWebServerUrl = mockWebServer.url("/v1/crawling").toString();
    }

    @AfterEach
    void terminate() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void should_title_is_returned_When_the_webClient_server_responds_successfully() {
        // webClient 서버가 성공적으로 응답하는 경우, 정상적으로 title이 반환되는지 확인
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockScrap)
                .addHeader("Content-Type", "application/json"));

        //when
        WebClientBodyResponse webClientBodyResponse = webClientService.crawlingItem(
                mockWebServerUrl, "https://www.naver.com");

        //then
        assertThat(webClientBodyResponse.getTitle()).isEqualTo("서울역");
    }

    @Test
    void should_it_returns_null_When_property_is_not_in_the_response_body_value() {
        // 응답 바디 값에 없는 author 속성의 경우, 해당 속성 값이 null로 반환되는지 확인
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockScrap)
                .addHeader("Content-Type", "application/json"));

        //when
        WebClientBodyResponse webClientBodyResponse = webClientService.crawlingItem(
                mockWebServerUrl, "https://www.naver.com");

        //then
        assertThat(webClientBodyResponse.getAuthor()).isEqualTo(null);
    }

    @Test
    void should_it_returns_null_When_property_value_of_the_response_body_is_different_from_the_webClientBodyResponse_property_value() {
        // 응답 바디의 속성 값이 webClientBodyResponse 속성 값과 다른 경우, null로 반환되는지 확인
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockScrap)
                .addHeader("Content-Type", "application/json"));

        //when
        WebClientBodyResponse webClientBodyResponse = webClientService.crawlingItem(
                mockWebServerUrl, "https://www.naver.com");

        //then
        assertThat(webClientBodyResponse.getHomepageUrl()).isEqualTo(null);
    }

    @Test
    void should_it_returns_null_When_webClient_server_responds_unsuccessfully() {
        // webClient 서버가 정상적으로 응답을 주지 않는 경우, null로 반환되는지 확인
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
        );

        //when
        WebClientBodyResponse webClientBodyResponse = webClientService.crawlingItem(
                mockWebServerUrl, "https://www.naver.com");

        //then
        assertThat(webClientBodyResponse).isEqualTo(null);
    }
}
