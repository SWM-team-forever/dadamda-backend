package com.forever.dadamda.repository;

import com.forever.dadamda.entity.scrap.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
