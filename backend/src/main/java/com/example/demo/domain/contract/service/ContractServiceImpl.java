package com.example.demo.domain.contract.service;

import com.example.demo.domain.contract.Merchandise;
import com.example.demo.domain.contract.Ticket;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.tickets.repository.TicketRepository;
import com.example.demo.domain.tickets.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;

import java.util.Optional;

@Service
@Slf4j
public class ContractServiceImpl implements ContractService {
    @Autowired
    private Ticket ticketContract;
    @Autowired
    private Merchandise merchandiseContract;
    @Autowired
    private Credentials credentials;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public void mintTicket(String username, String email) {
        log.info("mintTicke={},{}",username,email);
        try {
            ticketContract.fill(username, email).send();
            ticketContract.mintTicket().send();
            String total = ticketContract.total().send().toString();
            Optional<Member> member = memberRepository.findMemberByUsernameAndEmail(username,email);
            com.example.demo.domain.tickets.entity.Ticket ticket
                    = com.example.demo.domain.tickets.entity.Ticket.builder()
                    .member(member.get())
                    .tokenId(Integer.parseInt(total))
                    .build();
            ticketRepository.save(ticket);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    @Override
    public void burnTicket() {

    }

    @Override
    public void enter(int tokenId) {

    }

}
