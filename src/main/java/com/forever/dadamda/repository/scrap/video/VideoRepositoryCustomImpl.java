package com.forever.dadamda.repository.scrap.video;

import static com.forever.dadamda.entity.scrap.QVideo.video;

import com.forever.dadamda.entity.scrap.Video;
import com.forever.dadamda.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class VideoRepositoryCustomImpl implements VideoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Video> searchKeywordInVideoOrderByCreatedDateDesc(User user, String keyword,
            Pageable pageable) {
        List<Video> contents = queryFactory
                .selectFrom(video)
                .where(
                        video.user.eq(user)
                                .and(video.deletedDate.isNull())
                                .and(video.title.containsIgnoreCase(keyword)
                                        .or(video.description.containsIgnoreCase(keyword)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(video.createdDate.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<Video> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
