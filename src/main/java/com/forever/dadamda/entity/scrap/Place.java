package com.forever.dadamda.entity.scrap;

import com.forever.dadamda.entity.user.User;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Place extends Scrap {

    @Column(length = 255)
    private String address;

    @Column(precision = 16, scale = 14)
    private BigDecimal latitude;

    @Column(precision = 17, scale = 14)
    private BigDecimal longitude;

    @Column(length = 30)
    private String phoneNumber;

    @Column(length = 30)
    private String zipCode;

    @Column(length = 2083)
    private String homepageUrl;

    @Column(length = 100)
    private String category;

    @Builder
    Place(User user, String pageUrl, String title, String thumbnailUrl, String description,
            String address, BigDecimal latitude, BigDecimal longitude, String phoneNumber,
            String zipCode, String homepageUrl, String category, String siteName) {
        super(user, pageUrl, title, thumbnailUrl, description, siteName);
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.homepageUrl = homepageUrl;
        this.category = category;
    }
}
