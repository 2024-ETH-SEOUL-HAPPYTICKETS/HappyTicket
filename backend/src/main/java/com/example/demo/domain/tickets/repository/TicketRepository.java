package com.example.demo.domain.tickets.repository;

import com.example.demo.domain.tickets.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByMemberId(int memberId);
    Optional<Ticket> findTicketByTokenIdAndMemberId(int tokenId , int memberId);
}
