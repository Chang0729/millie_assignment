package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.domain.DiscountType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true) //
public class DiscountTypeConverter implements AttributeConverter<DiscountType, String> {

    @Override
    public String convertToDatabaseColumn(DiscountType attribute) {
        if (attribute == null) return null;
        return attribute.getCode();
    }

    @Override
    public DiscountType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        return Stream.of(DiscountType.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할인 타입 코드: " + dbData));
    }
}