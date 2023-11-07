package com.forever.dadamda.repository.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BoardRepositoryCustom {

    Slice<Board> getBoardsList(User user, Pageable pageable);

    Slice<Board> searchKeywordInBoardList(User user, String keyword, Pageable pageable);

    Optional<Boolean> findIsSharedByBoardUUID(User user, UUID boardUUID);

    Optional<Boolean> findIsPublicByBoardUUID(User user, UUID boardUUID);

    Slice<Board> getTrendBoardListOrderByHeartCnt(LocalDateTime trendStartDateTime,
            LocalDateTime trendEndDateTime, Pageable pageable, String tag);
}
