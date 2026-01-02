package com.millie.assignment.application.port.in;

import com.millie.assignment.domain.DiscountType;
import com.millie.assignment.domain.Product;
import com.millie.assignment.domain.ProductStatus;
import com.millie.assignment.domain.DiscountType;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

// @Data: Lombok이 getter, setter, toString 등을 자동 생성합니다.
@Data
// @Builder: Lombok이 빌더 패턴을 제공하여 객체 생성 시 가독성을 높입니다.
@Builder
// ProductResponse: API 응답용 DTO이며, 도메인 객체와 매핑됩니다.
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal originalPrice;
    private BigDecimal finalPrice; // 계산된 최종 가격 (할인 적용 후)

    private DiscountType discountType;
    private BigDecimal value;
    private BigDecimal discountPercent;
    private ProductStatus productStatus;
    private boolean isCouponApplicable;
    private Integer stockQuantity;
    private java.time.LocalDateTime saleStartAt;
    private java.time.LocalDateTime saleEndAt;

    public static ProductResponse from(Product product, BigDecimal finalPrice) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .originalPrice(product.getOriginalPrice())
                .finalPrice(finalPrice)
                .discountType(product.getDiscountType())
                .value(product.getValue())
                .discountPercent(product.getDiscountPercent())
                .productStatus(product.getProductStatus())
                .isCouponApplicable(product.isCouponApplicable())
                .stockQuantity(product.getStockQuantity())
                .saleStartAt(product.getSaleStartAt())
                .saleEndAt(product.getSaleEndAt())
                .build();
    }
}