package com.forever.dadamda.repository.board;

import static com.forever.dadamda.entity.board.QBoard.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
                                .and(board.name.containsIgnoreCase(keyword))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(board.fixedDate.desc().nullsLast(), board.modifiedDate.desc())
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
