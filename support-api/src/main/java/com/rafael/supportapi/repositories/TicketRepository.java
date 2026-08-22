package com.rafael.supportapi.repositories;

import com.rafael.supportapi.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository
        extends JpaRepository<Ticket, Long> {
}