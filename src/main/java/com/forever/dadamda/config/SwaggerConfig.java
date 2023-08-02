package com.forever.dadamda.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "다담다 API 명세서",
                description = "신개념 컨텐츠 맞춤 스크랩 서비스, 다담다",
                version = "v1"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Server"),
                @Server(url = "https://api.dadamda.me:8080", description = "Product Server")
        }
)

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/v1/**"};

        return GroupedOpenApi.builder()
                .group("다담다 API v1")
                .pathsToMatch(paths)
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .build();
    }

    public OpenApiCustomiser buildSecurityOpenApi() {
        return OpenApi -> OpenApi.addSecurityItem(new SecurityRequirement().addList("X-AUTH-TOKEN"))
                .getComponents().addSecuritySchemes("X-AUTH-TOKEN", new SecurityScheme()
                        .name("X-AUTH-TOKEN")
                        .type(Type.APIKEY)
                        .in(In.HEADER));
    }
}
