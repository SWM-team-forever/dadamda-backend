package com.forever.dadamda.repository.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Optional<Board> findByUserAndTitle(User user, String title);

    Optional<Board> findByUserAndIdAndDeletedDateIsNull(User user, Long boardId);

    Long countByUserAndDeletedDateIsNull(User user);
}
