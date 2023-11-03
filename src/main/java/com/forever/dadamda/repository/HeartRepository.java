package com.forever.dadamda.repository;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.heart.Heart;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUserAndBoardAndDeletedDateIsNull(User user, Board board);
}
