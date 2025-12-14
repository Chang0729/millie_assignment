package com.millie.assignment.application.exception;

/**
 * 쿠폰이 존재하지 않을 때 발생하는 예외입니다.
 * RuntimeException을 상속해 unchecked 예외로 처리합니다.
 */
public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(Long couponId) {
        super("쿠폰(ID=" + couponId + ")이(가) 존재하지 않습니다.");
    }
}
