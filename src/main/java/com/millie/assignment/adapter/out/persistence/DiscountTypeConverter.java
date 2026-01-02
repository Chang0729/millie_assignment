package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.domain.DiscountType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

// @Converter(autoApply = true): JPA가 엔티티 필드에 자동 적용되는 변환기임을 지정합니다.
@Converter(autoApply = true)
// DiscountTypeConverter는 DiscountType enum과 DB 문자열 사이의 변환을 담당합니다.
public class DiscountTypeConverter implements AttributeConverter<DiscountType, String> {

    // convertToDatabaseColumn: 엔티티 필드 값을 DB 컬럼 문자열로 변환합니다.
    @Override
    public String convertToDatabaseColumn(DiscountType attribute) {
        if (attribute == null)
            return null;
        return attribute.getCode();
    }

    // convertToEntityAttribute: DB 문자열을 엔티티 필드 값(DiscountType)으로 변환합니다.
    @Override
    public DiscountType convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        return Stream.of(DiscountType.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할인 타입 코드: " + dbData));
    }
}