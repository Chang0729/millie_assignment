package com.millie.assignment.adapter.out.persistence;

import com.millie.assignment.application.port.out.LoadCouponPort;
import com.millie.assignment.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
// 이 클래스는 쿠폰 데이터를 로드하는 Outbound Adapter 역할을 수행합니다.
// LoadCouponPort 인터페이스를 구현하여 도메인 레이어에 쿠폰 조회 기능을 제공합니다.
public class CouponPersistenceAdapter implements LoadCouponPort {

    private final CouponRepository couponRepository;
    private final CouponPersistenceMapper mapper;

    @Override
    public Optional<Coupon> loadCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .map(mapper::mapToDomain);
    }
}