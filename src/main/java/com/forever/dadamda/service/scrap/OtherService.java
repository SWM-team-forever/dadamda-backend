package com.forever.dadamda.service.scrap;

import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.OtherRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OtherService {

    private final OtherRepository otherRepository;

    @Transactional
    public Other saveOther(JSONObject crawlingResponse, User user, String pageUrl) {
        Other other = Other.builder().user(user).pageUrl(pageUrl)
                .title(crawlingResponse.get("title").toString())
                .thumbnailUrl(crawlingResponse.get("thumbnail_url").toString())
                .description(crawlingResponse.get("description").toString())
                .siteName(crawlingResponse.get("site_name").toString()).build();

        return otherRepository.save(other);
    }
}
