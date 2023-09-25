package com.forever.dadamda.entity.board;

import com.forever.dadamda.entity.BaseTimeEntity;
import com.forever.dadamda.entity.user.User;
import java.time.LocalDateTime;
import java.util.UUID;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "boolean", nullable = false)
    private boolean isPublic;

    private LocalDateTime fixedDate;

    @Column(nullable = false)
    private TAG tag;

    @Column(length = 1000)
    private String description;

    @ColumnDefault("0")
    private Long heartCnt;

    @ColumnDefault("0")
    private Long viewCnt;

    @ColumnDefault("0")
    private Long shareCnt;

    @Builder
    Board(User user, String name, TAG tag, UUID uuid, String description, boolean isPublic) {
        this.user = user;
        this.name = name;
        this.tag = tag;
        this.uuid = uuid;
        this.description = description;
        this.isPublic = isPublic;
    }
}
