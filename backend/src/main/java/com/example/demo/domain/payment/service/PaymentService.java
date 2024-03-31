package com.example.demo.domain.payment.service;

import com.example.demo.domain.payment.dto.PaymentDto;
import com.example.demo.domain.payment.dto.RefundRequestDto;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface PaymentService {
    PaymentDto verifyPayment(String impUid, String username, String email) throws Exception;

    boolean findPaymentByImpuid(RefundRequestDto refundRequestDto);
}
