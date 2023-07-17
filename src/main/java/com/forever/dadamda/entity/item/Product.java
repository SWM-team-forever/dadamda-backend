package com.forever.dadamda.entity.item;

import com.forever.dadamda.entity.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Product extends Item {

    @Column(length = 100)
    private String price;

    @Column(length = 100)
    private String siteName;

    @Builder
    Product(User user, String pageUrl, String title, String thumbnailUrl, String description,
            String price, String siteName) {
        super(user, pageUrl, title, thumbnailUrl, description);
        this.price = price;
        this.siteName = siteName;
    }
}
