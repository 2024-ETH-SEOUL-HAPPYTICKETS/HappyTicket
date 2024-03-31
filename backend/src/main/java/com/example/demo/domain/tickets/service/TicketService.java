package com.example.demo.domain.tickets.service;

import com.example.demo.domain.tickets.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getTickets(int username);

    void transferTicket(int id, int tokenId, String to);

    String checkTicket(int id, int tokenId);

    void save(Ticket ticket);
}
