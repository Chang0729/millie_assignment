package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.application.port.out.LoadCouponPort;
import com.millie.assignment.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponPersistenceAdapter implements LoadCouponPort {

    private final CouponRepository couponRepository;
    private final CouponPersistenceMapper mapper;

    @Override
    public Optional<Coupon> loadCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .map(mapper::mapToDomain);
    }
}