package com.forever.dadamda.repository.scrap.other;

import static com.forever.dadamda.entity.scrap.QOther.other;

import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class OtherRepositoryCustomImpl implements OtherRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Other> searchKeywordInOtherOrderByCreatedDateDesc(User user, String keyword,
            Pageable pageable) {
        List<Other> contents = queryFactory
                .selectFrom(other)
                .where(
                        other.user.eq(user)
                                .and(other.deletedDate.isNull())
                                .and(other.title.containsIgnoreCase(keyword)
                                        .or(other.description.containsIgnoreCase(keyword)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(other.createdDate.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<Other> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
