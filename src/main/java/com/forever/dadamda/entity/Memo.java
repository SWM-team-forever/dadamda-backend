package com.forever.dadamda.entity;

import com.forever.dadamda.entity.scrap.Scrap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Memo {

    @Id
    @GeneratedValue
    @Column(name = "memo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_id", nullable = false)
    private Scrap scrap;

    @Column(length = 1000)
    private String memo;

    @Column(length = 2083)
    private String memoImageUrl;
}
