package com.millie.assignment.application.port.in;

import com.millie.assignment.domain.Coupon;

public interface GetCouponUseCase {
    Coupon getValidCoupon(Long couponId);
}