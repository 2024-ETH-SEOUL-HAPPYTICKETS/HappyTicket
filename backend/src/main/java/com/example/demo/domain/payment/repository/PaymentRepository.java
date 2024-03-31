package com.example.demo.domain.payment.repository;

import com.example.demo.domain.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity , String> {
    public long countByImpuidContainsIgnoreCase(String impuid);
    Optional<PaymentEntity> findPaymentEntitiesByImpuid(String impuid);
}
