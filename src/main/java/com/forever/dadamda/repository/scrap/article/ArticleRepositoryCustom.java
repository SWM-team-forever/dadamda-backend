package com.forever.dadamda.repository.scrap.article;

import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {

    Slice<Article> searchKeywordInArticleOrderByCreatedDateDesc(
            User user, String keyword, Pageable pageable);

}
