package com.millie.assignment.application.service;

import com.millie.assignment.application.exception.CouponNotFoundException;
import com.millie.assignment.application.exception.ProductNotFoundException;
import com.millie.assignment.application.port.out.LoadCouponPort;
import com.millie.assignment.application.port.out.LoadProductPort;
import com.millie.assignment.domain.Coupon;
import com.millie.assignment.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * 서비스 레이어에서 존재하지 않는 상품·쿠폰 조회 시 예외가 발생하는지 검증합니다.
 */
@ExtendWith(MockitoExtension.class)
class ServiceExceptionTest {

    @Mock
    private LoadProductPort loadProductPort;

    @Mock
    private LoadCouponPort loadCouponPort;

    @InjectMocks
    private ProductService productService;

    @InjectMocks
    private CouponService couponService;

    @Test
    @DisplayName("존재하지 않는 상품 조회 시 ProductNotFoundException 발생")
    void productNotFoundThrows() {
        // given
        Long missingProductId = 999L;
        when(loadProductPort.loadProduct(missingProductId)).thenReturn(Optional.empty());
        // when & then
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductDetails(missingProductId, null));
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 조회 시 CouponNotFoundException 발생")
    void couponNotFoundThrows() {
        // given
        Long missingCouponId = 888L;
        when(loadCouponPort.loadCoupon(missingCouponId)).thenReturn(Optional.empty());
        // when & then
        assertThrows(CouponNotFoundException.class,
                () -> couponService.getValidCoupon(missingCouponId));
    }
}
