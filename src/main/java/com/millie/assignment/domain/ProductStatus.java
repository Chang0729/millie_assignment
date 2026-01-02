package com.millie.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 이 enum은 도메인 레이어에서 상품의 판매 상태를 정의합니다. Hexagonal Architecture에서 Domain 모델에
// 해당합니다.
public enum ProductStatus {
    ON_SALE("10", "판매중"),
    STOP_SALE("20", "판매중지"),
    END_SALE("30", "판매종료"),
    SOLD_OUT("40", "품절");

    private final String code; // DB 저장용 코드 ("10")
    private final String description; // 문서화/로그용 설명 ("정상")
}