package com.example.demo.domain.tickets.controller;

import com.example.demo.domain.tickets.dto.CheckRequestDto;
import com.example.demo.domain.tickets.dto.TransferRequestDto;
import com.example.demo.domain.tickets.entity.Ticket;
import com.example.demo.domain.tickets.service.TicketService;
import com.example.demo.utillities.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tickets")
@Slf4j
public class TicketsController {
    @Autowired
    TicketService ticketsService;

    @GetMapping
    public ResponseEntity<?> getTickets (@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Ticket> tickets = ticketsService.getTickets(principalDetails.getId());
        return ResponseEntity.ok().body(tickets);
    }

    @PostMapping
    public ResponseEntity<?> ticketTransfer (@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody TransferRequestDto transferRequestDto) {
        ticketsService.transferTicket(principalDetails.getId(), transferRequestDto.getTokenId() , transferRequestDto.getTo());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkTicket (@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CheckRequestDto checkRequestDto) {
        String UsedTicket = ticketsService.checkTicket(principalDetails.getId(), checkRequestDto.getId());
        return ResponseEntity.ok(UsedTicket);
    }
}
