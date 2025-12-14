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
public abstract class BaseJpaEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime rgstOc; //등록일시

    @LastModifiedDate
    private LocalDateTime lstAltrOc; //최근변경일시

    @CreatedBy
    @Column(updatable = false)
    private String rgstEmpNo; //등록사원번호

    @LastModifiedBy
    private String lstAltrEmpNo; //변경사원번호
}