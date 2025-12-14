package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.domain.DiscountType;
import com.millie.assignment.domain.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 값을 DB가 자동으로 증가시킴 (Auto Increment)
    private Long id;

    @Column(length = 50)
    private String name;            // 상품명

    @Column(precision = 15, scale = 2)
    private BigDecimal originalPrice;     // 정가

    @Column(length = 1)
    private DiscountType discountType;  // 퍼센트, 금액 할인 방법

    @Column(precision = 15, scale = 2)
    private BigDecimal value; // 할인 값 (퍼센트면 10, 금액이면 1000 등)

    @Column(length = 2)
    private ProductStatus productStatus;    // 상품상태코드

    @Column(nullable = false)
    private boolean isCouponApplicable; // 쿠폰 사용 가능 상품 여부.

    @Column(nullable = false)
    private Integer stockQuantity;  // 재고수량

    private LocalDateTime saleStartAt; // 판매 시작일시

    private LocalDateTime saleEndAt;   // 판매 종료일시

}
