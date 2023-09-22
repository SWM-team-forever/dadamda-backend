package com.forever.dadamda.repository.scrap.video;

import com.forever.dadamda.entity.scrap.Video;
import com.forever.dadamda.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface VideoRepositoryCustom {

    Slice<Video> searchKeywordInVideoOrderByCreatedDateDesc(
            User user, String keyword, Pageable pageable);
}
