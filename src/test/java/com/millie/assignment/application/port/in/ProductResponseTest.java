package com.millie.assignment.application.port.in;

import com.millie.assignment.domain.DiscountType;
import com.millie.assignment.domain.Product;
import com.millie.assignment.domain.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseTest {

    @Test
    @DisplayName("Product 객체로부터 ProductResponse가 올바르게 생성되는지 검증")
    void from_mapsAllFieldsCorrectly() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .originalPrice(BigDecimal.valueOf(10000))
                .discountType(DiscountType.PERCENTAGE)
                .value(BigDecimal.valueOf(10))
                .discountPercent(BigDecimal.valueOf(10))
                .productStatus(ProductStatus.ON_SALE)
                .isCouponApplicable(true)
                .stockQuantity(100)
                .saleStartAt(now.minusDays(1))
                .saleEndAt(now.plusDays(7))
                .rgstEmpNo("admin")
                .rgstOc(now)
                .lstAltrEmpNo("admin")
                .lstAltrOc(now)
                .build();

        BigDecimal finalPrice = BigDecimal.valueOf(9000);

        // when
        ProductResponse response = ProductResponse.from(product, finalPrice);

        // then
        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getOriginalPrice()).isEqualTo(product.getOriginalPrice());
        assertThat(response.getFinalPrice()).isEqualTo(finalPrice);
        assertThat(response.getDiscountType()).isEqualTo(product.getDiscountType());
        assertThat(response.getValue()).isEqualTo(product.getValue());
        assertThat(response.getDiscountPercent()).isEqualTo(product.getDiscountPercent());
        assertThat(response.getProductStatus()).isEqualTo(product.getProductStatus());
        assertThat(response.isCouponApplicable()).isEqualTo(product.isCouponApplicable());
        assertThat(response.getStockQuantity()).isEqualTo(product.getStockQuantity());
        assertThat(response.getSaleStartAt()).isEqualTo(product.getSaleStartAt());
        assertThat(response.getSaleEndAt()).isEqualTo(product.getSaleEndAt());
    }
}
