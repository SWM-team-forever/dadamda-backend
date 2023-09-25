package com.forever.dadamda.repository.scrap.other;

import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface OtherRepositoryCustom {

    Slice<Other> searchKeywordInOtherOrderByCreatedDateDesc(
            User user, String keyword, Pageable pageable);

}
