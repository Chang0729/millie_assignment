package com.millie.assignment.application.port.in;

import java.util.List;

// GetProductUseCase: 상품 조회 비즈니스 로직을 정의하는 Inbound Port 인터페이스입니다.
public interface GetProductUseCase {

    // 전체 상품 목록을 가져오는 기능 정의
    // getAllProducts: 전체 상품 목록을 반환합니다.
    List<ProductResponse> getAllProducts();

    // 상품 상세 정보를 가져오는 기능 정의 (상품 ID와 선택적 쿠폰 ID를 받음)
    // getProductDetails: 특정 상품 상세 정보를 반환합니다. (옵션으로 쿠폰 적용)
    ProductResponse getProductDetails(Long productId, Long couponId);
}