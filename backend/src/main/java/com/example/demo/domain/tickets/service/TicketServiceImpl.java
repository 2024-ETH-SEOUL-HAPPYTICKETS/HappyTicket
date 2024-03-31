package com.example.demo.domain.tickets.service;

import com.example.demo.domain.contract.service.ContractService;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.tickets.dto.TicketDescriptionDto;
import com.example.demo.domain.tickets.entity.Ticket;
import com.example.demo.domain.tickets.repository.TicketRepository;
import com.example.demo.exception.ticket.TicketNotOwnedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketRepository ticketsRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ContractService contractService;
    @Override
    public List<Ticket> getTickets(int id) {
        return ticketsRepository.findAllByMemberId(id);
    }

    @Override
    public void transferTicket(int id, int tokenId, String to) {
       Optional<Ticket> optionalTicket = ticketsRepository.findTicketByTokenIdAndMemberId(tokenId,id);
       Optional<Member> optionalMember = memberRepository.findMemberByUsername(to);
       Ticket ticket = optionalTicket.get();
       ticket.setMember(optionalMember.get());
       ticketsRepository.save(ticket);
    }

    @Override
    public String checkTicket(int id, int tokenId) throws TicketNotOwnedException {
        Optional<Ticket> optionalTicket = ticketsRepository.findTicketByTokenIdAndMemberId(tokenId,id);
        if( optionalTicket.isEmpty()) {
          throw new TicketNotOwnedException();
        }
        contractService.enter(tokenId);

        return null;
    }

    @Override
    public void save(Ticket ticket) {
        ticketsRepository.save(ticket);
    }
}
