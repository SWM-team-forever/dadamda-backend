package com.forever.dadamda.entity;

import com.forever.dadamda.entity.scrap.Scrap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "memo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_id", nullable = false)
    private Scrap scrap;

    @Column(length = 1000)
    private String memoText;

    @Column(length = 2083)
    private String memoImageUrl;

    @Builder
    public Memo(Scrap scrap, String memoText, String memoImageUrl){
        this.scrap = scrap;
        this.memoText = memoText;
        this.memoImageUrl = memoImageUrl;
    }

    public void update(String memoText, String memoImageUrl){
        this.memoText = memoText;
        this.memoImageUrl = memoImageUrl;
    }
}
