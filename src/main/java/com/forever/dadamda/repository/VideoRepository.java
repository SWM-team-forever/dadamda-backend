package com.forever.dadamda.repository;

import com.forever.dadamda.entity.scrap.Video;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Slice<Video>> findAllByUserAndDeletedDateIsNull(User user, Pageable pageable);
}
