package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import com.forever.dadamda.dto.scrap.product.GetProductResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.MemoRepository;
import com.forever.dadamda.repository.scrap.product.ProductRepository;
import com.forever.dadamda.service.user.UserService;
import lombok.RequiredArgsConstructor;
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
    private final MemoRepository memoRepository;

    @Transactional
    public Product saveProduct(WebClientBodyResponse crawlingResponse, User user, String pageUrl) {

        Product product = Product.builder().user(user).pageUrl(pageUrl)
                .title(crawlingResponse.getTitle())
                .thumbnailUrl(crawlingResponse.getThumbnailUrl())
                .price(crawlingResponse.getPrice())
                .siteName(crawlingResponse.getSiteName()).build();

        return productRepository.save(product);
    }

    @Transactional
    public void updateProduct(User user, UpdateScrapRequest updateScrapRequest) {
        Product product = productRepository.findByIdAndUserAndDeletedDateIsNull(
                        updateScrapRequest.getScrapId(), user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));
        product.update(updateScrapRequest.getTitle(), updateScrapRequest.getDescription(),
                updateScrapRequest.getSiteName());
        product.updateProduct(updateScrapRequest.getPrice());
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

        Slice<Product> prdouctSlice = productRepository.findAllByUserAndDeletedDateIsNull(user,
                pageRequest).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return prdouctSlice.map(product -> GetProductResponse.of(product,
                memoRepository.findMemosByScrapAndDeletedDateIsNull(product)));
    }

    @Transactional
    public Slice<GetProductResponse> searchProducts(String email, String keyword,
            Pageable pageable) {
        User user = userService.validateUser(email);

        Slice<Product> prdouctSlice = productRepository.searchKeywordInProductOrderByCreatedDateDesc(
                user, keyword, pageable);

        return prdouctSlice.map(product -> GetProductResponse.of(product,
                memoRepository.findMemosByScrapAndDeletedDateIsNull(product)));
    }
}
