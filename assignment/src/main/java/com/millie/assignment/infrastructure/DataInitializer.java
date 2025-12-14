package com.millie.assignment.infrastructure; // 패키지명 확인

import com.millie.assignment.domain.DiscountType;
import com.millie.assignment.domain.ProductStatus;
import com.millie.assignment.domain.CouponStatus; // 패키지 경로 확인 필요
import com.millie.assignment.adapter.out.persistence.CouponJpaEntity;
import com.millie.assignment.adapter.out.persistence.CouponRepository;
import com.millie.assignment.adapter.out.persistence.ProductJpaEntity;
import com.millie.assignment.adapter.out.persistence.ProductRepository;
import com.millie.assignment.adapter.out.persistence.BaseJpaEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {

        // 데이터 중복 생성 방지 (이미 있으면 패스)
        if (productRepository.count() > 0) return;

        System.out.println(">>> [초기 데이터 생성 시작] 상품 및 쿠폰 데이터를 생성합니다...");

        // 공통 데이터
        LocalDateTime now = LocalDateTime.now();
        String adminUser = "ADMIN";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 1. 상품(Product) 더미 데이터 생성
        // 상품 1: 정률 10% 할인, 쿠폰 사용 가능
        ProductJpaEntity book1 = ProductJpaEntity.builder()
                .name("오버핏 셔츠")
                .originalPrice(BigDecimal.valueOf(15000))
                .discountType(DiscountType.PERCENTAGE)
                .value(BigDecimal.valueOf(10)) // 10%
                .productStatus(ProductStatus.ON_SALE)
                .isCouponApplicable(true)
                .stockQuantity(100)
                .saleStartAt(now.minusDays(1))
                .saleEndAt(now.plusYears(1))
                .build();
        setAuditInfo(book1, adminUser, now);

        // 상품 2: 할인 없음, 쿠폰 사용 불가
        ProductJpaEntity subscription = ProductJpaEntity.builder()
                .name("하의")
                .originalPrice(BigDecimal.valueOf(9900))
                .discountType(DiscountType.AMOUNT)
                .value(BigDecimal.ZERO)
                .productStatus(ProductStatus.ON_SALE)
                .isCouponApplicable(false)
                .stockQuantity(999)
                .saleStartAt(now.minusDays(1))
                .saleEndAt(now.plusYears(10))
                .build();
        setAuditInfo(subscription, adminUser, now);

        // 상품 3: 정액 2000원 할인
        ProductJpaEntity goods = ProductJpaEntity.builder()
                .name("운동화")
                .originalPrice(BigDecimal.valueOf(20000))
                .discountType(DiscountType.AMOUNT)
                .value(BigDecimal.valueOf(2000))
                .productStatus(ProductStatus.SOLD_OUT)
                .isCouponApplicable(true)
                .stockQuantity(0)
                .saleStartAt(now.minusMonths(1))
                .saleEndAt(now.plusMonths(1))
                .build();
        setAuditInfo(goods, adminUser, now);

        productRepository.saveAll(Arrays.asList(book1, subscription, goods));

        // 2. 쿠폰(Coupon) 더미 데이터 생성
        // 쿠폰 1: 신규 가입 1000원 할인 (정액)
        CouponJpaEntity coupon1 = CouponJpaEntity.builder()
                .couponNm("WELCOME_COUPON")
                .discountType(DiscountType.AMOUNT)
                .value(BigDecimal.valueOf(1000))
                .aplyStrtDt(now.format(dateFormatter))
                .aplyEndDt(now.plusMonths(1).format(dateFormatter))
                .status(CouponStatus.NORMAL)
                .build();
        setAuditInfo(coupon1, adminUser, now);

        // 쿠폰 2: 10% 할인 쿠폰 (정률)
        CouponJpaEntity coupon2 = CouponJpaEntity.builder()
                .couponNm("TEN_PERCENT_DISCOUNT")
                .discountType(DiscountType.PERCENTAGE)
                .value(BigDecimal.valueOf(10)) // 10%
                .aplyStrtDt(now.format(dateFormatter))
                .aplyEndDt(now.plusDays(7).format(dateFormatter))
                .status(CouponStatus.NORMAL)
                .build();
        setAuditInfo(coupon2, adminUser, now);

        couponRepository.saveAll(Arrays.asList(coupon1, coupon2));

        System.out.println(">>> [초기 데이터 생성 완료]");
    }

    private void setAuditInfo(BaseJpaEntity entity, String user, LocalDateTime time) {
        entity.setRgstEmpNo(user);
        entity.setRgstOc(time);
        entity.setLstAltrEmpNo(user);
        entity.setLstAltrOc(time);
    }
}