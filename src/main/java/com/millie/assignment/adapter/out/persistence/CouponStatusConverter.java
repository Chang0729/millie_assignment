package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.domain.CouponStatus;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

// @Converter(autoApply = true): JPA가 엔티티 필드에 자동으로 적용되는 변환기임을 지정합니다.
// CouponStatusConverter는 CouponStatus enum과 DB 문자열 사이의 변환을 담당합니다.
public class CouponStatusConverter implements AttributeConverter<CouponStatus, String> {

    // convertToDatabaseColumn: 엔티티 필드 값을 DB 컬럼 문자열로 변환합니다.
    @Override
    public String convertToDatabaseColumn(CouponStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    // convertToEntityAttribute: DB 문자열을 엔티티 필드 값(CouponStatus)으로 변환합니다.
    @Override
    public CouponStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(CouponStatus.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰 상태 코드입니다: " + dbData));
    }
}