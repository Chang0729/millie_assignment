package com.millie.assignment.application.service;

import com.millie.assignment.application.exception.ProductNotFoundException;
import com.millie.assignment.application.port.in.GetCouponUseCase;
import com.millie.assignment.application.port.in.GetProductUseCase;
import com.millie.assignment.application.port.in.ProductResponse;
import com.millie.assignment.application.port.out.LoadProductPort;
import com.millie.assignment.domain.Coupon;
import com.millie.assignment.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

// @Service: 이 클래스가 Spring Service Bean임을 선언하여 DI 대상이 됩니다.
@Service
// @RequiredArgsConstructor: Lombok이 final 필드에 대한 생성자를 자동 생성합니다.
@RequiredArgsConstructor
// @Transactional(readOnly = true): 메서드가 읽기 전용 트랜잭션임을 선언합니다.
@Transactional(readOnly = true)
// ProductService: 비즈니스 로직을 구현하는 서비스 클래스이며, GetProductUseCase 인터페이스를 구현합니다.
public class ProductService implements GetProductUseCase {

    // LoadProductPort: 아웃바운드 포트로, DB에서 상품 데이터를 로드합니다.
    private final LoadProductPort loadProductPort;
    // GetCouponUseCase: 쿠폰 조회 비즈니스 로직을 호출하는 Inbound Port입니다.
    private final GetCouponUseCase getCouponUseCase;

    // @Override: GetProductUseCase 인터페이스 메서드 구현
    @Override
    // getAllProducts: 전체 상품 목록을 조회하고 DTO 로 변환합니다.
    public List<ProductResponse> getAllProducts() {
        return loadProductPort.loadAllProducts().stream()
                .map(product -> ProductResponse.from(product, product.calculateProductPrice()))
                .collect(Collectors.toList());
    }

    // @Override: GetProductUseCase 인터페이스 메서드 구현
    @Override
    // getProductDetails: 특정 상품과 선택적 쿠폰을 이용해 최종 가격을 계산합니다.
    public ProductResponse getProductDetails(Long productId, Long couponId) {
        // 1. 상품 조회 – 존재하지 않으면 ProductNotFoundException 발생
        Product product = loadProductPort.loadProduct(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // 2. 쿠폰 조회 (null 허용)
        Coupon coupon = null;
        if (couponId != null) {
            coupon = getCouponUseCase.getValidCoupon(couponId);
        }

        // 3. 가격 계산
        BigDecimal finalPrice = product.calculateFinalPriceWithCoupon(coupon);

        return ProductResponse.from(product, finalPrice);
    }
}