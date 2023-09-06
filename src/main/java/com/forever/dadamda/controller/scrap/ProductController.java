package com.forever.dadamda.controller.scrap;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.product.GetProductCountResponse;
import com.forever.dadamda.dto.scrap.product.GetProductResponse;
import com.forever.dadamda.service.scrap.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 조회", description = "여러개의 상품을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/products")
    public ApiResponse<Slice<GetProductResponse>> getProducts(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(productService.getProducts(email, pageable));
    }

    @Operation(summary = "상품 스크랩 개수 조회", description = "상품 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/products/count")
    public ApiResponse<GetProductCountResponse> getProductScrap(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(
                GetProductCountResponse.of(productService.getProductCount(email)));
    }

    @Operation(summary = "상품 스크랩 검색", description = "타이틀과 설명의 키워드로 상품 스크랩을 검색할 수 있습니다.")
    @GetMapping("/v1/scraps/products/search")
    public ApiResponse<Slice<GetProductResponse>> searchProducts(
            @RequestParam("keyword") String keyword,
            Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(productService.searchProducts(email, keyword, pageable));
    }
}
