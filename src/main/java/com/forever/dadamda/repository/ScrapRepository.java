package com.forever.dadamda.repository;

import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByPageUrlAndUserAndDeletedDateIsNull(String pageUrl, User user);
    Optional<Scrap> findByIdAndUserAndDeletedDateIsNull(Long scrapId, User user);
}