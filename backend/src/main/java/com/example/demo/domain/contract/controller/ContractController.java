package com.example.demo.domain.contract.controller;

import com.example.demo.domain.contract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;


}
