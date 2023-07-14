package com.forever.dadamda.entity.item;

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
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "d_type")
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "item")
    private List<Memo> memoList = new ArrayList<>();

    @Column(length = 2083, nullable = false)
    private String pageUrl;

    @Column(length = 200)
    private String title;

    @Column(length = 2083)
    private String thumnailUrl;

    @Column(columnDefinition = "TEXT")
    private String description;
}
