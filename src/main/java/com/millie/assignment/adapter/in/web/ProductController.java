package com.millie.assignment.adapter.in.web;

import com.millie.assignment.application.port.in.GetProductUseCase;
import com.millie.assignment.application.port.in.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController: 이 클래스가 REST API 엔드포인트임을 선언합니다.
@RestController
// @RequestMapping("/product"): 기본 URL 경로를 지정합니다.
@RequestMapping("/product")
// @RequiredArgsConstructor: Lombok이 final 필드에 대한 생성자를 자동 생성합니다.
@RequiredArgsConstructor
// 이 클래스는 Hexagonal Architecture에서 Inbound Adapter 역할을 수행합니다.
public class ProductController {

    private final GetProductUseCase getProductUseCase;

    /**
     * 상품 전체 목록 조회 API
     * URL: GET /product
     * * @return 전체 상품 목록(ProductResponse 리스트)을 담은 응답
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(getProductUseCase.getAllProducts());
    }

    /**
     * 상품 상세 조회 API (옵션: 쿠폰 적용 가격 확인)
     * URL: GET /product/{id}?couponId=1
     * 예시: /product/1 (그냥 조회), /product/1?couponId=5 (5번 쿠폰 적용 가격 조회)
     *
     * @param id       URL 경로에 있는 상품 ID (Path Variable)
     * @param couponId 쿼리 파라미터로 넘어오는 쿠폰 ID (선택 사항, 없으면 null)
     * @return 단일 상품 상세 정보(ProductResponse)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductDetails(
            @PathVariable Long id,
            @RequestParam(required = false) Long couponId) {

        return ResponseEntity.ok(getProductUseCase.getProductDetails(id, couponId));
    }
}
