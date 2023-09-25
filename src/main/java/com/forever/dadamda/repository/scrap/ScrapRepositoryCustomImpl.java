package com.forever.dadamda.repository.scrap;

import static com.forever.dadamda.entity.scrap.QScrap.scrap;

import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class ScrapRepositoryCustomImpl implements ScrapRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Scrap> searchKeywordInScrapOrderByCreatedDateDesc(User user, String keyword,
            Pageable pageable) {
        List<Scrap> contents = queryFactory
                .selectFrom(scrap)
                .where(
                        scrap.user.eq(user)
                                .and(scrap.deletedDate.isNull())
                                .and(scrap.title.containsIgnoreCase(keyword)
                                        .or(scrap.description.containsIgnoreCase(keyword)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(scrap.createdDate.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<Scrap> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
