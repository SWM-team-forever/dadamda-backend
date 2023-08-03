package com.forever.dadamda.repository;

import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Slice<Article>> findAllByUserAndDeletedDateIsNull(User user, Pageable pageable);

    Optional<Article> findByIdAndUserAndDeletedDateIsNull(Long scrapId, User user);

    Long countByUserAndDeletedDateIsNull(User user);
}
