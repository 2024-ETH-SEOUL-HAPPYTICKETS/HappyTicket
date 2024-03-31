package com.example.demo.domain.payment.service;

import com.example.demo.domain.contract.service.ContractService;
import com.example.demo.domain.payment.dto.PaymentDto;
import com.example.demo.domain.payment.dto.RefundRequestDto;
import com.example.demo.domain.payment.entity.PaymentEntity;
import com.example.demo.domain.payment.repository.PaymentRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{
    private final IamportClient iamportClient;
    private final PaymentRepository paymentRepository;
    @Autowired
    private ContractService contractService;
    public PaymentServiceImpl( PaymentRepository paymentRepository) {
        this.iamportClient = new IamportClient("--- ApI Key ---", "---- API Secret Key ----");
        this.paymentRepository = paymentRepository;
    }

    public PaymentDto verifyPayment(String imp_uid, String username, String email) throws Exception {
        IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(imp_uid);
        Long amount = (paymentIamportResponse.getResponse().getAmount()).longValue();
        String name = paymentIamportResponse.getResponse().getName();
        String status = paymentIamportResponse.getResponse().getStatus();

        PaymentDto paymentDto = PaymentDto.builder()
                .impuid(imp_uid)
                .amount(amount)
                .status(status)
                .name(name)
                .build();

        if(paymentRepository.countByImpuidContainsIgnoreCase(imp_uid) ==0 ) {
            if (paymentIamportResponse.getResponse().getStatus().equals("paid")){
                PaymentEntity paymentEntity = new PaymentEntity(paymentDto);
                paymentRepository.save(paymentEntity);
                contractService.mintTicket(username,email);

                return paymentDto;
            }else {
                paymentDto.setStatus("Payment Error");
                return paymentDto;
            }
        } else {
            paymentDto.setStatus("Already Paid");
            return paymentDto;
        }
    }

    @Override
    public boolean findPaymentByImpuid(RefundRequestDto refundRequestDto) {
        Optional<PaymentEntity> paymentEntity = paymentRepository.findPaymentEntitiesByImpuid(refundRequestDto.getMerchant_uid());
        if (paymentEntity.isPresent()){
            PaymentEntity payment = paymentEntity.get();

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + "your_access_token"); // 실제 토큰으로 교체해야 합니다.

            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("imp_uid", payment.getImpuid());

//            requestMap.put("checksum", cancelableAmount);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);

            ResponseEntity<Map> responseEntity = restTemplate.postForEntity("https://api.iamport.kr/payments/cancel", entity, Map.class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                contractService.burnTicket();
                return true;
            }
        }
        return false;
    }
}
