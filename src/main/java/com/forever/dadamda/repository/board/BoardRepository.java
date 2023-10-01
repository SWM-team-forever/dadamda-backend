package com.forever.dadamda.repository.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Optional<Board> findByUserAndName(User user, String name);

    Optional<Board> findByUserAndIdAndDeletedDateIsNull(User user, Long boardId);
}
