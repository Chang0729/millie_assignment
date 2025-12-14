package com.millie.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponStatus {
    NORMAL("10", "정상"),
    TERMINATED("80", "해지");

    private final String code;       // DB 저장용 코드 ("10")
    private final String description; // 문서화/로그용 설명 ("정상")
}