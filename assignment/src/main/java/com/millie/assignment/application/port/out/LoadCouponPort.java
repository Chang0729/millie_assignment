package com.millie.assignment.application.port.out;

import com.millie.assignment.domain.Coupon;

import java.util.Optional;

public interface LoadCouponPort {
    Optional<Coupon> loadCoupon(Long id);
}

