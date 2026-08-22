package com.rafael.supportapi.patterns.state;

import com.rafael.supportapi.models.Ticket;

public interface TicketState {

    void start(Ticket ticket);

    void resolve(Ticket ticket);

    void reopen(Ticket ticket);
}