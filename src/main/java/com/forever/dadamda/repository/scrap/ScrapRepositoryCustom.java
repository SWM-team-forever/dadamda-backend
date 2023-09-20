package com.forever.dadamda.repository.scrap;

import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ScrapRepositoryCustom {
    Slice<Scrap> searchKeywordInScrapOrderByCreatedDateDesc(User user, String keyword, Pageable pageable);
}
