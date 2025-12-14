package com.millie.assignment.application.service;

import com.millie.assignment.application.exception.CouponNotFoundException;
import com.millie.assignment.application.port.in.GetCouponUseCase;
import com.millie.assignment.application.port.out.LoadCouponPort;
import com.millie.assignment.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService implements GetCouponUseCase {

    private final LoadCouponPort loadCouponPort;

    @Override
    public Coupon getValidCoupon(Long couponId) {
        // 1. 쿠폰 조회 – 존재하지 않으면 예외 발생
        Coupon coupon = loadCouponPort.loadCoupon(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));

        // 2. 현재 날짜 기준으로 유효성 검증
        coupon.validateValidity(LocalDate.now());

        return coupon;
    }
}
