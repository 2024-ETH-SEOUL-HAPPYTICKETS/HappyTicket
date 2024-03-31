package com.example.demo.domain.payment.entity;

import com.example.demo.domain.payment.dto.PaymentDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String impuid;
    private String name;
    private String status;
    private Long amount;

    public PaymentEntity(PaymentDto paymentDto){
        this.impuid = paymentDto.getImpuid();
        this.amount = paymentDto.getAmount();
        this.name = paymentDto.getName();
        this.status = paymentDto.getStatus();
    }
}
