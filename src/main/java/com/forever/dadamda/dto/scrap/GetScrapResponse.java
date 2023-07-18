package com.forever.dadamda.dto.scrap;

import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.scrap.Video;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetScrapResponse {

    private Long scrapId;
    private String dType;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;
    private String author;
    private String authorImageUrl;
    private String blogName;
    private LocalDateTime publishedDate;
    private String price;
    private String channelImageUrl;
    private String channelName;
    private String embedUrl;
    private String genre;
    private Long playTime;
    private Long watchedCnt;

    public static GetScrapResponse of(Scrap scrap) {
        GetScrapResponseBuilder getScrapResponse = new GetScrapResponseBuilder()
                .scrapId(scrap.getId())
                .createdDate(scrap.getCreatedDate())
                .modifiedDate(scrap.getModifiedDate())
                .description(scrap.getDescription())
                .pageUrl(scrap.getPageUrl())
                .siteName(scrap.getSiteName())
                .thumbnailUrl(scrap.getThumbnailUrl())
                .title(scrap.getTitle());

        if (scrap instanceof Article) {
            Article article = (Article) scrap;
            getScrapResponse.author(article.getAuthor())
                    .authorImageUrl(article.getAuthorImageUrl())
                    .blogName(article.getBlogName())
                    .publishedDate(article.getPublishedDate());
        } else if (scrap instanceof Video) {
            Video video = (Video) scrap;
            getScrapResponse.embedUrl(video.getEmbedUrl())
                    .channelImageUrl(video.getChannelImageUrl())
                    .channelName(video.getChannelName())
                    .genre(video.getGenre())
                    .playTime(video.getPlayTime())
                    .watchedCnt(video.getWatchedCnt());
        } else if (scrap instanceof Product) {
            Product product = (Product) scrap;
            getScrapResponse.price(product.getPrice());
        }

        return getScrapResponse.build();
    }
}
