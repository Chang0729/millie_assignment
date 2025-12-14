package com.millie.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountType {
    PERCENTAGE("1","PERCENTAGE"),
    AMOUNT("2","AMOUNT");

    private final String code;       // DB 저장용 코드 ("10")
    private final String description; // 문서화/로그용 설명 ("정상")
}