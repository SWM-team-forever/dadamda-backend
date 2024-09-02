수정일 : 2023-08-04 12:15

# Dadamda Backend

## 📰 프로젝트 소개
모든 사이트의 URL을 담을 수 있고 여러 스크랩들을 모아 사용자가 보드를 꾸미고 공유할 수 있는 웹 서비스입니다.

## 🐳 개발 환경
- Java 11 `Amazon Corretto version 11.0.20`
- Spring Boot `2.7.13`
- Gradle `7.6.1`
- JPA `3.1.2`
- AWS RDS `MySQL 8.0.32`
- GitHub Actions

## 🖥️ 관리 도구
- 형상 관리 : Github
- 이슈 관리 : Jira
- 커뮤니케이션 : Confluence
- 디자인 : Figma

## 💭 프로젝트 구조

```bash
src
 ┣ main
 ┃ ┣ generated
 ┃ ┣ java
 ┃ ┃ ┗ com
 ┃ ┃ ┃ ┗ forever
 ┃ ┃ ┃ ┃ ┗ dadamda
 ┃ ┃ ┃ ┃ ┃ ┣ config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ MemoController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ScrapController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserController.java
 ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ scrap
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CreateHighlightRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CreateHighlightResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CreateMemoRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CreateScrapRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CreateScrapResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetArticleCountResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetArticleResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetMemoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetOtherCountResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetOtherResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetProductCountResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetProductResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetScrapCountResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetScrapResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetVideoCountResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetVideoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UpdateScrapRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetProfileUrlResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetUserInfoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ OAuthAttributes.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ApiResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ ErrorCode.java
 ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ scrap
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Article.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Other.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Product.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Scrap.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ Video.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Provider.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Role.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ BaseTimeEntity.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ Memo.java
 ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ advice
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ControllerExceptionAdvice.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ GeneralException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ InternalServerException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ InvalidException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ NotFoundException.java
 ┃ ┃ ┃ ┃ ┃ ┣ filter
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ JwtAuthFilter.java
 ┃ ┃ ┃ ┃ ┃ ┣ handler
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ OAuth2SuccessHandler.java
 ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ArticleRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ MemoRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ OtherRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ProductRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ScrapRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ VideoRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ scrap
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ArticleService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OtherService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ProductService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ScrapService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ VideoService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CustomOAuth2UserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ MemoService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ TimeService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ TokenService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ WebClientService.java
 ┃ ┃ ┃ ┃ ┃ ┗ DadamdaApplication.java
 ┃ ┗ resources
 ┃ ┃ ┣ application-dev-secret.yml
 ┃ ┃ ┣ application-prod-secret.yml
 ┃ ┃ ┗ application.yml
 ┗ test
 ┃ ┗ java
 ┃ ┃ ┗ com
 ┃ ┃ ┃ ┗ forever
 ┃ ┃ ┃ ┃ ┗ dadamda
 ┃ ┃ ┃ ┃ ┃ ┗ DadamdaApplicationTests.java
```

# 📂 ERD

```mermaid
erDiagram
    USER ||--o{ SCRAP : ""
    SCRAP ||--o{ ARTICLE : ""
    SCRAP ||--o{ VIDEO : ""
    SCRAP ||--o{ PRODUCT : ""
    SCRAP ||--o{ OTHER : ""
    SCRAP ||--o{ MEMO : ""
    SCRAP ||--o{ SCRAP_BOARD : ""
    BOARD ||--o{ SCRAP_BOARD : ""
    USER ||--|{ HEART : ""
    BOARD ||--|{ HEART : ""
    USER ||--o{ BOARD : ""

    ENTITY BASETIME {
    timestamp CREATED_DATE
    timestamp UPDATED_DATE
    timestamp DELETED_DATE
}

    ENTITY USER {
    BIGINT ID PK
    VARCHAR(100) NAME
    VARCHAR(320) EMAIL
    VARCHAR(2083) PROFILE_URL
    ENUM PROVIDER
    ENUM ROLE
    VARCHAR(36) UUID
}

    ENTITY SCRAP {
    BIGINT ID PK
    BIGINT USER_ID FK
    ENUM D_TYPE
    VARCHAR(2083) PAGE_URL
    VARCHAR(200) TITLE
    VARCHAR(2083) THUMBNAIL_URL
    text DESCRIPTION
    VARCHAR(100) SITE_NAME
}

    ENTITY ARTICLE {
    BIGINT SCRAP_ID PK, FK
    text AUTHOR
    VARCHAR(2083) AUTHOR_IMAGE_URL
    timestamp PUBLISHED_DATE
    VARCHAR(100) BLOG_NAME
}

    ENTITY VIDEO {
    BIGINT SCRAP_ID PK, FK
    VARCHAR(2083) EMBED_URL
    VARCHAR(100) CHANNEL_NAME
    VARCHAR(2083) CHANNEL_IMAGE_URL
    BIGINT WATCHED_CNT
    BIGINT PLAY_TIME
    timestamp PUBLISHED_DATE
    VARCHAR(100) GENRE
}

    ENTITY PRODUCT {
    BIGINT SCRAP_ID PK, FK
    VARCHAR(100) PRICE
}

    ENTITY OTHER {
    BIGINT SCRAP_ID PK, FK
}

    ENTITY MEMO {
    BIGINT ID PK
    BIGINT SCRAP_ID FK
    VARCHAR(1000) MEMO_TEXT
    VARCHAR(2083) MEMO_IMAGE_URL
}

    ENTITY BOARD {
    BIGINT ID PK
    VARCHAR(36) UUID
    BIGINT USER_ID FK
    VARCHAR(100) NAME
    BOOLEAN IS_PUBLIC
}

    ENTITY SCRAP_BOARD {
    BIGINT BOARD_ID PK
    BIGINT SCRAP_ID FK
    BIGINT MEMO_ID FK
    DOUBLE X
    DOUBLE Y
    DOUBLE WIDTH
    DOUBLE HEIGHT
}

    ENTITY HEART {
    BIGINT ID PK
    BIGINT USER_ID FK
    BIGINT BOARD_ID FK
}

```
