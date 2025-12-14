package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.domain.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductPersistenceMapper {

    // 1. Entity -> Domain
    public Product mapToDomain(ProductJpaEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .originalPrice(entity.getOriginalPrice())

                // ✅ 추가된 할인 정보 매핑
                .discountType(entity.getDiscountType())
                .value(entity.getValue())

                .productStatus(entity.getProductStatus())
                .isCouponApplicable(entity.isCouponApplicable())
                .stockQuantity(entity.getStockQuantity())
                .saleStartAt(entity.getSaleStartAt())
                .saleEndAt(entity.getSaleEndAt())

                // Audit
                .rgstEmpNo(entity.getRgstEmpNo())
                .rgstOc(entity.getRgstOc())
                .lstAltrEmpNo(entity.getLstAltrEmpNo())
                .lstAltrOc(entity.getLstAltrOc())
                .build();
    }

    // 2. Domain(자바) -> Entity(DB) 변환
    public ProductJpaEntity mapToEntity(Product domain) {

        // 과제용 하드코딩 데이터
        LocalDateTime now = LocalDateTime.now();
        String adminUser = "ADMIN";

        ProductJpaEntity entity = ProductJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .originalPrice(domain.getOriginalPrice())

                .discountType(domain.getDiscountType())
                .value(domain.getValue())

                .productStatus(domain.getProductStatus())
                .isCouponApplicable(domain.isCouponApplicable())
                .stockQuantity(domain.getStockQuantity())

                .saleStartAt(domain.getSaleStartAt())
                .saleEndAt(domain.getSaleEndAt())
                .build();

        entity.setRgstEmpNo(domain.getRgstEmpNo() != null ? domain.getRgstEmpNo() : adminUser);
        entity.setRgstOc(domain.getRgstOc() != null ? domain.getRgstOc() : now);

        entity.setLstAltrEmpNo(adminUser);
        entity.setLstAltrOc(now);

        return entity;
    }
}