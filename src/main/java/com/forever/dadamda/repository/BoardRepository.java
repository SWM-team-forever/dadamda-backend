package com.forever.dadamda.repository;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findFirstByUserAndDeletedDateIsNull(User user);

    Optional<Board> findByUserAndIdAndDeletedDateIsNull(User user, Long boardId);
}
