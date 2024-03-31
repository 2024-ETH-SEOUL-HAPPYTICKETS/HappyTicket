package com.example.demo.domain.contract.service;

import com.example.demo.domain.tickets.dto.TicketDescriptionDto;

public interface ContractService {

    void mintTicket(String username, String email) throws Exception;

    void burnTicket();

    void enter(int tokenId);
}
