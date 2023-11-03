package com.forever.dadamda.entity.board;

import com.forever.dadamda.dto.board.UpdateBoardContentsRequest;
import com.forever.dadamda.dto.board.UpdateBoardRequest;
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
    private String title;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isPublic;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isShared;

    private LocalDateTime fixedDate;

    @Column(nullable = false)
    private TAG tag;

    @Column(length = 1000)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @ColumnDefault("0")
    private Long heartCnt;

    @ColumnDefault("0")
    private Long viewCnt;

    @ColumnDefault("0")
    private Long shareCnt;

    @Column(length = 2084)
    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorship_id", nullable = false)
    private User authorshipUser;

    @Builder
    Board(User user, String title, TAG tag, UUID uuid, String description,
            LocalDateTime fixedDate, User authorshipUser, String contents) {
        this.user = user;
        this.title = title;
        this.tag = tag;
        this.uuid = uuid;
        this.description = description;
        this.fixedDate = fixedDate;
        this.authorshipUser = authorshipUser;
        this.contents = contents;
    }

    public void updateFixedDate(LocalDateTime fixedDate) {
        this.fixedDate = fixedDate;
    }

    public void updateBoard(UpdateBoardRequest request) {
        this.title = request.getTitle();
        this.tag = TAG.from(request.getTag());
        this.description = request.getDescription();
    }

    public void updateContents(UpdateBoardContentsRequest request) {
        this.contents = request.getContents();
    }

    public void updateThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void updateIsShared(Boolean request) {
        this.isShared = request;
    }

    public void updateIsPublic(Boolean request) {
        this.isPublic = request;
    }
}
