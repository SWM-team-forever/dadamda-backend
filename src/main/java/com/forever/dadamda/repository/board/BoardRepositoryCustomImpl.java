package com.forever.dadamda.repository.board;

import static com.forever.dadamda.entity.board.QBoard.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import com.forever.dadamda.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Board> getBoardsList(User user, Pageable pageable) {
        List<Board> contents = queryFactory.selectFrom(board)
                .where(
                        board.user.eq(user)
                                .and(board.deletedDate.isNull())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(board.fixedDate.desc().nullsLast(), board.modifiedDate.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    @Override
    public Slice<Board> searchKeywordInBoardList(User user, String keyword, Pageable pageable) {
        List<Board> contents = queryFactory.selectFrom(board)
                .where(
                        board.user.eq(user)
                                .and(board.deletedDate.isNull())
                                .and(board.title.containsIgnoreCase(keyword))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(board.fixedDate.desc().nullsLast(), board.modifiedDate.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    @Override
    public Optional<Boolean> findIsSharedByBoardUUID(User user, UUID boardUUID) {

        Boolean isShared = queryFactory.select(board.isShared)
                .from(board)
                .where(
                        board.user.eq(user)
                                .and(board.uuid.eq(boardUUID))
                                .and(board.deletedDate.isNull())
                )
                .fetchOne();
        return Optional.ofNullable(isShared);
    }

    @Override
    public Optional<Boolean> findIsPublicByBoardUUID(User user, UUID boardUUID) {

        Boolean isPublic = queryFactory.select(board.isPublic)
                .from(board)
                .where(
                        board.user.eq(user)
                                .and(board.uuid.eq(boardUUID))
                                .and(board.deletedDate.isNull())
                )
                .fetchOne();
        return Optional.ofNullable(isPublic);
    }

    @Override
    public Slice<Board> getTrendBoardListOrderByHeartCnt(LocalDateTime trendStartDateTime,
            LocalDateTime trendEndDateTime, Pageable pageable, String tag) {
        List<Board> contents = queryFactory.selectFrom(board)
                .where(
                        board.deletedDate.isNull()
                                .and(board.isPublic.isTrue())
                                .and(board.createdDate.between(trendStartDateTime, trendEndDateTime))
                                .and(tag == null ? null : board.tag.eq(TAG.from(tag)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(board.heartCnt.desc(), board.shareCnt.desc(), board.viewCnt.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    @Override
    public List<User> getPopularUsersByHeartTotalCnt(LocalDateTime startDate, LocalDateTime endDate,
            Long limit) {
        return queryFactory.select(board.user)
                .where(
                        board.deletedDate.isNull()
                                .and(board.isPublic.isTrue())
                                .and(board.user.deletedDate.isNull())
                                .and(board.createdDate.between(startDate, endDate))
                )
                .from(board)
                .groupBy(board.user)
                .having(board.count().gt(0))
                .orderBy(board.heartCnt.sum().desc(), board.shareCnt.sum().desc(),
                        board.viewCnt.sum().desc(), board.count().desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public Slice<Board> searchKeywordInTrendBoardList(LocalDateTime startDate,
            LocalDateTime endDate, String keyword, Pageable pageable) {
        List<Board> contents = queryFactory.selectFrom(board)
                .where(
                        board.isPublic.isTrue()
                                .and(board.deletedDate.isNull())
                                .and(board.title.containsIgnoreCase(keyword))
                                .and(board.createdDate.between(startDate, endDate))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(board.heartCnt.desc(), board.shareCnt.desc(), board.viewCnt.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<Board> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
