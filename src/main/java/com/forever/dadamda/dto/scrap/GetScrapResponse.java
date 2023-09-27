package com.forever.dadamda.dto.scrap;

import com.forever.dadamda.dto.memo.GetMemoResponse;
import com.forever.dadamda.entity.Memo;
import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.scrap.Video;
import com.forever.dadamda.service.TimeService;
import com.forever.dadamda.service.scrap.VideoService;
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
public class GetScrapResponse {

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

    // Product 부분
    private String price;

    // Video 부분
    private String channelImageUrl;
    private String channelName;
    private String embedUrl;
    private String playTime;
    private String watchedCnt;

    public static GetScrapResponse of(Scrap scrap, List<Memo> memoList) {
        GetScrapResponseBuilder getScrapResponse = new GetScrapResponseBuilder()
                .dType("other")
                .scrapId(scrap.getId())
                .description(scrap.getDescription())
                .pageUrl(scrap.getPageUrl())
                .siteName(scrap.getSiteName())
                .thumbnailUrl(scrap.getThumbnailUrl())
                .title(scrap.getTitle())
                .memoList(memoList.stream().map(GetMemoResponse::of)
                        .collect(Collectors.toList()));

        if (scrap instanceof Article) {
            Article article = (Article) scrap;
            getScrapResponse.dType("article")
                    .author(article.getAuthor())
                    .authorImageUrl(article.getAuthorImageUrl())
                    .blogName(article.getBlogName())
                    .publishedDate(TimeService.fromLocalDateTime(article.getPublishedDate()));
        } else if (scrap instanceof Video) {
            Video video = (Video) scrap;
            getScrapResponse.dType("video")
                    .embedUrl(video.getEmbedUrl())
                    .channelImageUrl(video.getChannelImageUrl())
                    .channelName(video.getChannelName())
                    .playTime(VideoService.formatPlayTime(video.getPlayTime()))
                    .watchedCnt(VideoService.formatViewCount(video.getWatchedCnt()))
                    .publishedDate(TimeService.fromLocalDateTime(video.getPublishedDate()));
        } else if (scrap instanceof Product) {
            Product product = (Product) scrap;
            getScrapResponse.dType("product")
                    .price(product.getPrice());
        }

        return getScrapResponse.build();
    }
}
