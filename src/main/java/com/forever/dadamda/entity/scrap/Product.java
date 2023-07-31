package com.forever.dadamda.entity.scrap;

import com.forever.dadamda.entity.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Product extends Scrap {

    @Column(length = 100)
    private String price;

    @Builder
    Product(User user, String pageUrl, String title, String thumbnailUrl, String description,
            String price, String siteName) {
        super(user, pageUrl, title, thumbnailUrl, description, siteName);
        this.price = price;
    }

    public void updateProduct(String price) {
        this.price = price;
    }
}
