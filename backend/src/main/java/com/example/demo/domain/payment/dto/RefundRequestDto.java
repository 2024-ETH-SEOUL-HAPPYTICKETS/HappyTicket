package com.example.demo.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundRequestDto {
    private String merchant_uid;
    private Long cancel_request_amount;
    private String reason;
}
