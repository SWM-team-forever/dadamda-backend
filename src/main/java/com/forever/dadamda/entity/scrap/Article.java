package com.forever.dadamda.entity.scrap;

import com.forever.dadamda.entity.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article extends Scrap {

    @Column(length = 100)
    private String author;

    @Column(length = 2083)
    private String authorImageUrl;

    private LocalDateTime publishedDate;

    @Column(length = 100)
    private String blogName;

    @Builder
    Article(User user, String pageUrl, String title, String thumbnailUrl, String description,
            String author, String authorImageUrl, String siteName,
            LocalDateTime publishedDate,
            String blogName) {
        super(user, pageUrl, title, thumbnailUrl, description, siteName);
        this.author = author;
        this.authorImageUrl = authorImageUrl;
        this.publishedDate = publishedDate;
        this.blogName = blogName;
    }

    public void updateArticle(String author, String authorImageUrl, LocalDateTime publishedDate,
            String blogName) {
        this.author = author;
        this.authorImageUrl = authorImageUrl;
        this.publishedDate = publishedDate;
        this.blogName = blogName;
    }
}