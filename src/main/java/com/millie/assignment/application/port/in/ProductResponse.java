package com.millie.assignment.application.port.in;

import com.millie.assignment.domain.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data // Getter, Setter, toString 등을 자동 생성
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal originalPrice;
    private BigDecimal finalPrice; // 계산된 최종 가격 (할인 적용 후)

    private com.millie.assignment.domain.DiscountType discountType;
    private BigDecimal value;
    private BigDecimal discountPercent;
    private com.millie.assignment.domain.ProductStatus productStatus;
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