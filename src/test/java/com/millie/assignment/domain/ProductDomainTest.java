package com.millie.assignment.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link Product} 비즈니스 로직(가격 계산 및 쿠폰 적용) 테스트.
 */
class ProductDomainTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2025, 1, 1, 0, 0);

    @Test
    @DisplayName("상품 가격에 대한 퍼센트 할인")
    void calculateProductPrice_percentageDiscount() {
        Product product = Product.builder()
                .id(1L)
                .name("TestProduct")
                .originalPrice(BigDecimal.valueOf(1000))
                .discountType(DiscountType.PERCENTAGE)
                .value(BigDecimal.valueOf(10)) // 10%
                .discountPercent(BigDecimal.valueOf(10))
                .productStatus(ProductStatus.ON_SALE)
                .isCouponApplicable(true)
                .stockQuantity(10)
                .saleStartAt(NOW.minusDays(1))
                .saleEndAt(NOW.plusDays(1))
                .rgstEmpNo("E001")
                .rgstOc(NOW)
                .lstAltrEmpNo("E001")
                .lstAltrOc(NOW)
                .build();
        // 1000 - 10% = 900, 이미 10원 단위
        assertEquals(BigDecimal.valueOf(900), product.calculateProductPrice());
    }

    @Test
    @DisplayName("상품 가격에 대한 금액 할인")
    void calculateProductPrice_amountDiscount() {
        Product product = Product.builder()
                .id(2L)
                .name("TestProduct2")
                .originalPrice(BigDecimal.valueOf(1000))
                .discountType(DiscountType.AMOUNT)
                .value(BigDecimal.valueOf(150)) // 150원 discount
                .discountPercent(BigDecimal.ZERO)
                .productStatus(ProductStatus.ON_SALE)
                .isCouponApplicable(true)
                .stockQuantity(5)
                .saleStartAt(NOW.minusDays(1))
                .saleEndAt(NOW.plusDays(1))
                .rgstEmpNo("E002")
                .rgstOc(NOW)
                .lstAltrEmpNo("E002")
                .lstAltrOc(NOW)
                .build();
        // 1000 - 150 = 850, 이미 10원 단위
        assertEquals(BigDecimal.valueOf(850), product.calculateProductPrice());
    }

    @Test
    @DisplayName("퍼센트 쿠폰 적용 후 최종 가격")
    void calculateFinalPriceWithCoupon_percentageCoupon() {
        Product product = Product.builder()
                .id(3L)
                .name("Prod")
                .originalPrice(BigDecimal.valueOf(1000))
                .discountType(DiscountType.PERCENTAGE)
                .value(BigDecimal.valueOf(10)) // 10% product discount => 900
                .discountPercent(BigDecimal.valueOf(10))
                .productStatus(ProductStatus.ON_SALE)
                .isCouponApplicable(true)
                .stockQuantity(3)
                .saleStartAt(NOW.minusDays(1))
                .saleEndAt(NOW.plusDays(1))
                .rgstEmpNo("E003")
                .rgstOc(NOW)
                .lstAltrEmpNo("E003")
                .lstAltrOc(NOW)
                .build();
        Coupon coupon = Coupon.builder()
                .id(1L)
                .couponNm("10% OFF")
                .discountType(DiscountType.PERCENTAGE)
                .value(BigDecimal.valueOf(10)) // 10% coupon
                .status(CouponStatus.NORMAL)
                .aplyStrtDt(LocalDate.now().minusDays(1))
                .aplyEndDt(LocalDate.now().plusDays(1))
                .rgstEmpNo("E001")
                .rgstOc(LocalDateTime.now())
                .lstAltrEmpNo("E001")
                .lstAltrOc(LocalDateTime.now())
                .build();
        // 상품 할인 후 기본 가격 = 900, 쿠폰 10% 적용 → 90 할인, 최종 = 810
        assertEquals(BigDecimal.valueOf(810), product.calculateFinalPriceWithCoupon(coupon));
    }

    @Test
    @DisplayName("금액 쿠폰 적용 및 쿠폰 사용 불가 상품에 대한 최종 가격")
    void calculateFinalPriceWithCoupon_amountCoupon_nonApplicable() {
        Product product = Product.builder()
                .id(4L)
                .name("Prod")
                .originalPrice(BigDecimal.valueOf(1000))
                .discountType(DiscountType.AMOUNT)
                .value(BigDecimal.valueOf(100)) // 100원 product discount => 900
                .discountPercent(BigDecimal.ZERO)
                .productStatus(ProductStatus.ON_SALE)
                .isCouponApplicable(false) // 쿠폰은 무시되어야 함
                .stockQuantity(2)
                .saleStartAt(NOW.minusDays(1))
                .saleEndAt(NOW.plusDays(1))
                .rgstEmpNo("E004")
                .rgstOc(NOW)
                .lstAltrEmpNo("E004")
                .lstAltrOc(NOW)
                .build();
        Coupon coupon = Coupon.builder()
                .id(2L)
                .couponNm("500원 OFF")
                .discountType(DiscountType.AMOUNT)
                .value(BigDecimal.valueOf(500))
                .status(CouponStatus.NORMAL)
                .aplyStrtDt(LocalDate.now().minusDays(1))
                .aplyEndDt(LocalDate.now().plusDays(1))
                .rgstEmpNo("E001")
                .rgstOc(LocalDateTime.now())
                .lstAltrEmpNo("E001")
                .lstAltrOc(LocalDateTime.now())
                .build();
        // 쿠폰 무시, 기본 가격 = 900
        assertEquals(BigDecimal.valueOf(900), product.calculateFinalPriceWithCoupon(coupon));
    }
}
