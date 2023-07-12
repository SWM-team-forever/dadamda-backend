package com.forever.dadamda.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "다담다 API 명세서",
                description = "신개념 컨텐츠 맞춤 스크랩 서비스, 다담다",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/api/v1/**"};

        return GroupedOpenApi.builder()
                .group("다담다 API v1")
                .pathsToMatch(paths)
                .build();
    }
}
