package com.forever.dadamda.entity.scrap;

import com.forever.dadamda.entity.BaseTimeEntity;
import com.forever.dadamda.entity.Memo;
import com.forever.dadamda.entity.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@DiscriminatorColumn(name = "d_type")
public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "scrap")
    private List<Memo> memoList = new ArrayList<>();

    @Column(length = 2083, nullable = false)
    private String pageUrl;

    @Column(length = 200)
    private String title;

    @Column(length = 2083)
    private String thumbnailUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String siteName;

    public Scrap(User user, String pageUrl) {
        this.user = user;
        this.pageUrl = pageUrl;
    }

    public Scrap(User user, String pageUrl, String title, String thumbnailUrl, String description, String siteName) {
        this.user = user;
        this.pageUrl = pageUrl;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.siteName = siteName;
    }
}
