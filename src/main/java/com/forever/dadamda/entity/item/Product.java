package com.forever.dadamda.entity.item;

import javax.persistence.Column;

public class Product extends Item {

    @Column(length = 100)
    private String price;

    @Column(length = 100)
    private String siteName;
}
