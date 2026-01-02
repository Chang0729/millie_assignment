package com.millie.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@ToString
// 이 클래스는 도메인 레이어에서 상품 정보를 표현합니다. Hexagonal Architecture에서 Domain 모델에 해당합니다.
public class Product {
    private Long id;
    private String name;
    private BigDecimal originalPrice; // 정가

    // [변경된 할인 구조]
    private DiscountType discountType; // 할인 유형 (PERCENTAGE, AMOUNT)
    private BigDecimal value; // 할인 값 (10% or 1000원)
    private BigDecimal discountPercent; // (참고용/기본) 할인율 - 필요 시 유지

    private ProductStatus productStatus;
    private boolean isCouponApplicable;
    private Integer stockQuantity;
    private LocalDateTime saleStartAt;
    private LocalDateTime saleEndAt;

    // [Audit]
    private String rgstEmpNo;
    private LocalDateTime rgstOc;
    private String lstAltrEmpNo;
    private LocalDateTime lstAltrOc;

    @Builder
    public Product(Long id, String name, BigDecimal originalPrice,
            DiscountType discountType, BigDecimal value, BigDecimal discountPercent,
            ProductStatus productStatus, boolean isCouponApplicable, Integer stockQuantity,
            LocalDateTime saleStartAt, LocalDateTime saleEndAt,
            String rgstEmpNo, LocalDateTime rgstOc, String lstAltrEmpNo, LocalDateTime lstAltrOc) {

        this.id = id;
        this.name = name;
        this.originalPrice = originalPrice;
        this.discountType = discountType;
        this.value = value != null ? value : BigDecimal.ZERO;
        this.discountPercent = discountPercent != null ? discountPercent : BigDecimal.ZERO;

        this.productStatus = productStatus;
        this.isCouponApplicable = isCouponApplicable;
        this.stockQuantity = stockQuantity;
        this.saleStartAt = saleStartAt;
        this.saleEndAt = saleEndAt;

        this.rgstEmpNo = rgstEmpNo;
        this.rgstOc = rgstOc;
        this.lstAltrEmpNo = lstAltrEmpNo;
        this.lstAltrOc = lstAltrOc;

        validate();
    }

    private void validate() {
        if (this.originalPrice != null && this.originalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품 가격은 0원보다 작을 수 없습니다.");
        }
    }

    /**
     * 1. 상품 자체 최종 판매가 계산 (핵심 비즈니스 로직)
     * - DiscountType에 따라 정률/정액 계산
     * - 10원 단위 절사
     */
    public BigDecimal calculateProductPrice() {
        if (originalPrice == null)
            return BigDecimal.ZERO;
        // 할인이 없거나 타입이 없으면 정가 리턴
        if (discountType == null || value.compareTo(BigDecimal.ZERO) == 0) {
            return originalPrice;
        }

        BigDecimal discountAmount = BigDecimal.ZERO;

        // 할인 계산 분기
        if (discountType == DiscountType.PERCENTAGE) {
            // 원가 * (값 / 100)
            BigDecimal rate = value.divide(BigDecimal.valueOf(100));
            discountAmount = originalPrice.multiply(rate);
        } else if (discountType == DiscountType.AMOUNT) {
            // 정액 할인 (그냥 값 자체가 할인액)
            discountAmount = value;
        }

        BigDecimal finalPrice = originalPrice.subtract(discountAmount);

        // 0원 미만 방어
        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            finalPrice = BigDecimal.ZERO;
        }

        // 10원 단위 절사
        return finalPrice.setScale(-1, RoundingMode.DOWN).setScale(0);
    }

    /**
     * 2. 쿠폰까지 적용한 최종 금액 계산
     * 
     * @param coupon 적용하려는 쿠폰 (Nullable)
     */
    public BigDecimal calculateFinalPriceWithCoupon(Coupon coupon) {
        // Step 1: 상품 자체 할인가 먼저 계산
        BigDecimal basePrice = calculateProductPrice();

        // 1. 쿠폰이 없는 경우
        // 2. 이 상품이 '쿠폰 사용 불가(false)'로 설정된 경우 -> 쿠폰 무시하고 상품 할인가 리턴
        if (coupon == null || !this.isCouponApplicable) {
            return basePrice;
        }

        BigDecimal couponDiscountAmount = BigDecimal.ZERO;

        // Step 2: 쿠폰 타입별 계산
        if (coupon.getDiscountType() == DiscountType.AMOUNT) {
            // 정액 할인 (예: 1000원)
            couponDiscountAmount = coupon.getValue();
        } else if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            // 정률 할인 (예: 10%) -> 상품 할인가 기준
            BigDecimal rate = coupon.getValue().divide(BigDecimal.valueOf(100));
            couponDiscountAmount = basePrice.multiply(rate);
        }

        // Step 3: 차감
        BigDecimal finalPrice = basePrice.subtract(couponDiscountAmount);

        // 0원 미만 방지 (마이너스 결제 불가)
        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        // Step 4: 최종 10원 단위 절사
        return finalPrice.setScale(-1, RoundingMode.DOWN).setScale(0);
    }

    /**
     * 판매 가능 상태인지 확인하는 편의 메서드
     */
    public boolean isOnSale(LocalDateTime now) {
        // 상태가 SALE 이어야 하고
        if (this.productStatus != ProductStatus.ON_SALE) {
            return false;
        }
        // 판매 기간 내에 있어야 함 (Start <= now <= End)
        // null인 경우 기간 제한 없음으로 간주할지, 불가로 볼지는 정책 나름 (여기선 기간 필수 가정)
        if (saleStartAt != null && now.isBefore(saleStartAt))
            return false;
        if (saleEndAt != null && now.isAfter(saleEndAt))
            return false;

        return true;
    }
}