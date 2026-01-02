package com.millie.assignment.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
// 이 추상 클래스는 JPA 엔티티들의 공통 감시(Auditing) 필드를 제공하며, Outbound Adapter에서 DB 접근 시
// 상속됩니다.
public abstract class BaseJpaEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime rgstOc; // 등록일시

    @LastModifiedDate
    private LocalDateTime lstAltrOc; // 최근변경일시

    @CreatedBy
    @Column(updatable = false)
    private String rgstEmpNo; // 등록사원번호

    @LastModifiedBy
    private String lstAltrEmpNo; // 변경사원번호
}