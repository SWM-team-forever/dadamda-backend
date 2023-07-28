package com.forever.dadamda.service.scrap;

import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product saveProduct(JSONObject crawlingResponse, User user, String pageUrl) {
        Product product = Product.builder().user(user).pageUrl(pageUrl)
                .title(crawlingResponse.get("title").toString())
                .thumbnailUrl(crawlingResponse.get("thumbnail_url").toString())
                .price(crawlingResponse.get("price").toString())
                .siteName(crawlingResponse.get("site_name").toString()).build();

        return productRepository.save(product);
    }
}
