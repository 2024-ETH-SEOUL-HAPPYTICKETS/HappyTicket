package com.example.demo.domain.payment.controller;

import com.example.demo.domain.payment.dto.PaymentDto;
import com.example.demo.domain.payment.dto.RefundRequestDto;
import com.example.demo.domain.payment.service.PaymentService;
import com.example.demo.utillities.auth.PrincipalDetails;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api/v1/payment")
@Slf4j
@Controller
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/{imp_uid}")
    public PaymentDto paymentByImpUid(@PathVariable("imp_uid") String imp_uid, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        log.info(imp_uid);
        log.info(principalDetails.getUsername());
        log.info(principalDetails.getEmail());
        return paymentService.verifyPayment(imp_uid, principalDetails.getUsername(), principalDetails.getEmail());
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundRequest(@RequestBody RefundRequestDto refundRequestDto) {
        if(paymentService.findPaymentByImpuid(refundRequestDto)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }

}