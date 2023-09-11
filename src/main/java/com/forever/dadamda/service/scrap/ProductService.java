package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.product.GetProductResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.ProductRepository;
import java.util.Optional;
import com.forever.dadamda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

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

    @Transactional
    public Long getProductCount(String email) {
        User user = userService.validateUser(email);
        return productRepository.countByUserAndDeletedDateIsNull(user);
    }

    @Transactional
    public Slice<GetProductResponse> getProducts(String email, Pageable pageable) {
        User user = userService.validateUser(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);

        Slice<Product> scrapSlice = productRepository.findAllByUserAndDeletedDateIsNull(user,
                pageRequest).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return scrapSlice.map(GetProductResponse::of);
    }

    @Transactional
    public Slice<GetProductResponse> searchProducts(String email, String keyword, Pageable pageable) {
        User user = userService.validateUser(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);

        Slice<Product> scrapSlice = productRepository
                .findAllByUserAndDeletedDateIsNullAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        user, keyword, keyword, pageRequest)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return scrapSlice.map(GetProductResponse::of);
    }
}
