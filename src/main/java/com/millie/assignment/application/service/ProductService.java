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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements GetProductUseCase {

    private final LoadProductPort loadProductPort;
    private final GetCouponUseCase getCouponUseCase;

    @Override
    public List<ProductResponse> getAllProducts() {
        return loadProductPort.loadAllProducts().stream()
                .map(product -> ProductResponse.from(product, product.calculateProductPrice()))
                .collect(Collectors.toList());
    }

    @Override
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