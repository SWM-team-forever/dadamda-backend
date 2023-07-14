package com.forever.dadamda.entity.item;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Article extends Item {

    @Column(length = 100)
    private String author;

    @Column(length = 2083)
    private String authorImageUrl;

    @Column(length = 100)
    private String siteName;

    private LocalDateTime publishedDate;

    @Column(length = 100)
    private String blogName;
}