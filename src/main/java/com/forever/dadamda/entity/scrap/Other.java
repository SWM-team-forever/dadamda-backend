package com.forever.dadamda.entity.scrap;

import javax.persistence.Entity;

import com.forever.dadamda.entity.user.User;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Other extends Scrap{

    @Builder
    public Other(User user, String pageUrl, String title, String thumbnailUrl, String description, String siteName) {
        super(user, pageUrl, title, thumbnailUrl, description, siteName);
    }
}
