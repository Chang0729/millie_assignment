package com.millie.assignment.application.exception;

/**
 * 상품이 존재하지 않을 때 발생하는 예외입니다.
 * RuntimeException을 상속해 unchecked 예외로 처리합니다.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("상품(ID=" + productId + ")이(가) 존재하지 않습니다.");
    }
}
