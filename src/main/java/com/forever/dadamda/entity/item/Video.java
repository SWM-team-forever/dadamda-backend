package com.forever.dadamda.entity.item;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Video extends Item {

    @Column(length = 2083)
    private String embedUrl;

    @Column(length = 100)
    private String channelName;

    @Column(length = 2083)
    private String channelImageUrl;

    private Long watchedCnt;

    private Long playTime;

    private Date publishedDate;

    @Column(length = 100)
    private String siteName;

    @Column(length = 100)
    private String genre;
}