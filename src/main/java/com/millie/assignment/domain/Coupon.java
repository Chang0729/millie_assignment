package com.millie.assignment.domain;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class Coupon {
    private Long id;
    private String couponNm;
    private DiscountType discountType; // Enum
    private BigDecimal value; // 할인 값 (퍼센트면 10, 금액이면 1000 등)
    private CouponStatus status;       // Enum

    private LocalDate aplyStrtDt;
    private LocalDate aplyEndDt;

    private String rgstEmpNo; // 등록사원번호
    private LocalDateTime rgstOc; // 등록 일시
    private String lstAltrEmpNo; // 최종 변경 사원번호
    private LocalDateTime lstAltrOc; // 최종변경 일시

    public void validateValidity(LocalDate today) {
        // 1. 상태 체크
        if (this.status != CouponStatus.NORMAL) {
            throw new IllegalArgumentException("사용할 수 없는 상태의 쿠폰입니다.");
        }

        // 2. 날짜 체크 (LocalDate 메서드 사용으로 매우 직관적)
        // 로직: 시작일 이전이거나(Before), 종료일 이후면(After) -> 예외
        if (today.isBefore(this.aplyStrtDt)) {
            throw new IllegalArgumentException("쿠폰 사용 기간이 아닙니다.");
        }
        if (today.isAfter(this.aplyEndDt)) {
            throw new IllegalArgumentException("만료된 쿠폰입니다.");
        }
    }
}
