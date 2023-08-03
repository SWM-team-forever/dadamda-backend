package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.ProductRepository;
import java.util.Optional;
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
                .title(Optional.ofNullable(crawlingResponse.get("title")).map(Object::toString)
                        .orElse(null))
                .thumbnailUrl(Optional.ofNullable(crawlingResponse.get("thumbnail_url"))
                        .map(Object::toString).orElse(null))
                .price(Optional.ofNullable(crawlingResponse.get("price")).map(Object::toString)
                        .orElse(null))
                .siteName(
                        Optional.ofNullable(crawlingResponse.get("site_name")).map(Object::toString)
                                .orElse(null)).build();

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(User user, UpdateScrapRequest updateScrapRequest) {
        Product product = productRepository.findByIdAndUserAndDeletedDateIsNull(
                        updateScrapRequest.getScrapId(), user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));
        product.update(updateScrapRequest.getTitle(), updateScrapRequest.getDescription(),
                updateScrapRequest.getSiteName());
        product.updateProduct(updateScrapRequest.getPrice());
        return product;
    }
}
