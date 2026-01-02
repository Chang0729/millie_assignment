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

// @Entity: JPA 엔티티임을 선언합니다.
@Entity
// @Table(name = "coupon"): DB 테이블명을 지정합니다.
@Table(name = "coupon")
// @Getter: Lombok이 모든 필드에 대한 getter 메서드를 자동 생성합니다.
@Getter
// @Builder: Lombok이 빌더 패턴을 제공하여 객체 생성 시 가독성을 높입니다.
@Builder
// @NoArgsConstructor: 파라미터가 없는 기본 생성자를 Lombok이 생성합니다.
@NoArgsConstructor
// @AllArgsConstructor: 모든 필드를 파라미터로 받는 생성자를 Lombok이 생성합니다.
@AllArgsConstructor
// 이 엔티티는 JPA를 사용해 쿠폰 데이터를 DB에 저장/조회합니다. Hexagonal Architecture에서 Outbound
// Adapter 역할을 수행합니다.
public class CouponJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String couponNm; // 쿠폰명 (예: "WELCOME_COUPON")

    @Column(length = 1)
    private DiscountType discountType; // 퍼센트, 금액 할인 방법

    @Column(precision = 15, scale = 2)
    private BigDecimal value; // 할인 값 (퍼센트면 10, 금액이면 1000 등)

    @Column(length = 8)
    private String aplyStrtDt; // 쿠폰 적용 시작일자

    @Column(length = 8)
    private String aplyEndDt; // 쿠폰 적용 종료일자

    @Column(length = 2)
    private CouponStatus status; // 쿠폰 상태.
}
