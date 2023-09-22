package com.forever.dadamda.repository.scrap.article;

import static com.forever.dadamda.entity.scrap.QArticle.article;

import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Article> searchKeywordInArticleOrderByCreatedDateDesc(User user, String keyword,
            Pageable pageable) {
        List<Article> contents = queryFactory
                .selectFrom(article)
                .where(
                        article.user.eq(user)
                                .and(article.deletedDate.isNull())
                                .and(article.title.containsIgnoreCase(keyword)
                                        .or(article.description.containsIgnoreCase(keyword)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(article.createdDate.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<Article> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
