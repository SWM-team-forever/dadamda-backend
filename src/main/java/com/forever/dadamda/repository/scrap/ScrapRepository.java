package com.forever.dadamda.repository.scrap;

import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByPageUrlAndUserAndDeletedDateIsNull(String pageUrl, User user);

    Optional<Scrap> findByIdAndUserAndDeletedDateIsNull(Long scrapId, User user);

    Optional<Slice<Scrap>> findAllByUserAndDeletedDateIsNull(User user, Pageable pageable);

    Long countByUserAndDeletedDateIsNull(User user);
    Optional<Slice<Scrap>> findAllByUserAndDeletedDateIsNullAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(User user, String title, String description, Pageable pageable);
}
