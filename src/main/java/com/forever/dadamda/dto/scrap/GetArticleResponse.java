package com.forever.dadamda.dto.scrap;

import com.forever.dadamda.entity.scrap.Article;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetArticleResponse {

    // 공통 부분
    private Long scrapId;
    private String dType;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime deletedDate;
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;

    // Article 부분
    private String author;
    private String authorImageUrl;
    private String blogName;
    private LocalDateTime publishedDate;

    public static GetArticleResponse of(Article article) {
        return new GetArticleResponseBuilder()
                .scrapId(article.getId())
                .createdDate(article.getCreatedDate())
                .modifiedDate(article.getModifiedDate())
                .deletedDate(article.getDeletedDate())
                .description(article.getDescription())
                .pageUrl(article.getPageUrl())
                .siteName(article.getSiteName())
                .thumbnailUrl(article.getThumbnailUrl())
                .deletedDate(article.getDeletedDate())
                .title(article.getTitle())
                .author(article.getAuthor())
                .authorImageUrl(article.getAuthorImageUrl())
                .blogName(article.getBlogName())
                .publishedDate(article.getPublishedDate())
                .dType("article")
                .build();
    }
}
