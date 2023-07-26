package com.forever.dadamda.entity.scrap;

import com.forever.dadamda.entity.user.User;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Other extends Scrap {

    @Builder
    public Other(User user, String pageUrl, String title, String thumbnailUrl, String description,
            String siteName) {
        super(user, pageUrl, title, thumbnailUrl, description, siteName);
    }
}
