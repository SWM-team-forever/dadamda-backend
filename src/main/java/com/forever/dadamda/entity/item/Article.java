package com.forever.dadamda.entity.item;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Article extends Item {

    private String author;

    @Column(length = 2083)
    private String authorImageUrl;

    @Column(length = 100)
    private String siteName;

    private Date publishedDate;

    @Column(length = 100)
    private String blogName;
}