package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.domain.Coupon;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
// 이 클래스는 쿠폰 엔티티와 도메인 모델 간 변환을 담당하는 Outbound Adapter의 Mapper 역할을 수행합니다.
public class CouponPersistenceMapper {

    // yyyyMMdd 포맷 (비즈니스 날짜용)
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    // 1. Entity(DB) -> Domain(자바) 변환
    // Entity → Domain 변환 로직
    public Coupon mapToDomain(CouponJpaEntity entity) {
        return Coupon.builder()
                .id(entity.getId())
                .couponNm(entity.getCouponNm())
                .discountType(entity.getDiscountType())
                .value(entity.getValue())
                .status(entity.getStatus())

                .aplyStrtDt(LocalDate.parse(entity.getAplyStrtDt(), FORMATTER))
                .aplyEndDt(LocalDate.parse(entity.getAplyEndDt(), FORMATTER))

                .rgstEmpNo(entity.getRgstEmpNo())
                .rgstOc(entity.getRgstOc())
                .lstAltrEmpNo(entity.getLstAltrEmpNo())
                .lstAltrOc(entity.getLstAltrOc())
                .build();
    }

    // 2. Domain(자바) -> Entity(DB) 변환
    // Domain → Entity 변환 로직
    public CouponJpaEntity mapToEntity(Coupon domain) {

        // 과제용 하드코딩 데이터
        LocalDateTime now = LocalDateTime.now();
        String adminUser = "ADMIN";

        CouponJpaEntity entity = CouponJpaEntity.builder()
                .id(domain.getId())
                .couponNm(domain.getCouponNm())
                .discountType(domain.getDiscountType())
                .value(domain.getValue())
                .status(domain.getStatus())
                .aplyStrtDt(domain.getAplyStrtDt().format(FORMATTER))
                .aplyEndDt(domain.getAplyEndDt().format(FORMATTER))
                .build();

        entity.setRgstEmpNo(domain.getRgstEmpNo() != null ? domain.getRgstEmpNo() : adminUser);
        entity.setRgstOc(domain.getRgstOc() != null ? domain.getRgstOc() : now);

        entity.setLstAltrEmpNo(adminUser);
        entity.setLstAltrOc(now);

        return entity;
    }
}