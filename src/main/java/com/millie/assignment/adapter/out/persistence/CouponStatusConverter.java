package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.domain.CouponStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CouponStatusConverter implements AttributeConverter<CouponStatus, String> {

    @Override
    public String convertToDatabaseColumn(CouponStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

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