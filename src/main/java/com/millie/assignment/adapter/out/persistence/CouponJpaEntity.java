package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.domain.CouponStatus;
import com.millie.assignment.domain.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String couponNm; // 쿠폰명 (예: "WELCOME_COUPON")

    @Column(length = 1)
    private DiscountType discountType;  // 퍼센트, 금액 할인 방법

    @Column(precision = 15, scale = 2)
    private BigDecimal value; // 할인 값 (퍼센트면 10, 금액이면 1000 등)

    @Column(length = 8)
    private String aplyStrtDt; // 쿠폰 적용 시작일자

    @Column(length = 8)
    private String aplyEndDt; // 쿠폰 적용 종료일자

    @Column(length = 2)
    private CouponStatus status; // 쿠폰 상태.
}
