package com.forever.dadamda.repository;

import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtherRepository extends JpaRepository<Other, Long> {

    Optional<Slice<Other>> findAllByUserAndDeletedDateIsNull(User user, Pageable pageable);

    Optional<Other> findByIdAndUserAndDeletedDateIsNull(Long scrapId, User user);

}
