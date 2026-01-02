package com.millie.assignment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

// 이 인터페이스는 Spring Data JPA를 사용해 쿠폰 엔티티에 대한 CRUD 작업을 제공하는 Outbound Adapter 레이어의 Repository입니다.
public interface CouponRepository extends JpaRepository<CouponJpaEntity, Long> {
}
