package com.millie.assignment.application.port.in;

import java.util.List;

public interface GetProductUseCase {

    // 전체 상품 목록을 가져오는 기능 정의
    List<ProductResponse> getAllProducts();

    // 상품 상세 정보를 가져오는 기능 정의 (상품 ID와 선택적 쿠폰 ID를 받음)
    ProductResponse getProductDetails(Long productId, Long couponId);
}