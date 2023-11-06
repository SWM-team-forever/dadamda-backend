package com.forever.dadamda.repository.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Optional<Board> findByUserAndTitle(User user, String title);

    Optional<Board> findByUserAndIdAndDeletedDateIsNull(User user, Long boardId);

    Long countByUserAndDeletedDateIsNull(User user);

    Optional<Board> findByUserAndUuidAndDeletedDateIsNull(User user, UUID uuid);

    Optional<Board> findByUuidAndDeletedDateIsNullAndIsSharedIsTrue(UUID uuid);

    Optional<Board> findByUuidAndDeletedDateIsNullAndIsPublicIsTrue(UUID uuid);

    Optional<Board> findByUuidAndDeletedDateIsNull(UUID uuid);
}
