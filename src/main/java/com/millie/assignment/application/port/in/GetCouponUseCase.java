package com.millie.assignment.application.port.in;

import com.millie.assignment.domain.Coupon;

// GetCouponUseCase: 쿠폰 조회 비즈니스 로직을 정의하는 Inbound Port 인터페이스입니다.
public interface GetCouponUseCase {
    // getValidCoupon: 주어진 쿠폰 ID에 대해 유효한 쿠폰을 반환합니다. (존재하지 않으면 null)
    Coupon getValidCoupon(Long couponId);
}