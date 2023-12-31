package com.forever.dadamda.dto.scrap.article;

import com.forever.dadamda.dto.memo.GetMemoResponse;
import com.forever.dadamda.entity.Memo;
import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.service.TimeService;
import java.util.List;
import java.util.stream.Collectors;
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
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;
    private List<GetMemoResponse> memoList;

    // Article 부분
    private String author;
    private String authorImageUrl;
    private String blogName;
    private Long publishedDate;

    public static GetArticleResponse of(Article article, List<Memo> memoList) {
        return new GetArticleResponseBuilder()
                .dType("article")
                .scrapId(article.getId())
                .description(article.getDescription())
                .pageUrl(article.getPageUrl())
                .siteName(article.getSiteName())
                .thumbnailUrl(article.getThumbnailUrl())
                .title(article.getTitle())
                .author(article.getAuthor())
                .authorImageUrl(article.getAuthorImageUrl())
                .blogName(article.getBlogName())
                .publishedDate(TimeService.fromLocalDateTime(article.getPublishedDate()))
                .memoList(memoList.stream().map(GetMemoResponse::of).collect(Collectors.toList()))
                .build();
    }
}
