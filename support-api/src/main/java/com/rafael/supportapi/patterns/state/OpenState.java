package com.rafael.supportapi.patterns.state;

import com.rafael.supportapi.models.Ticket;
import com.rafael.supportapi.models.TicketStatus;

public class OpenState implements TicketState {

    @Override
    public void start(Ticket ticket) {

        ticket.setStatus(TicketStatus.IN_PROGRESS);
    }

    @Override
    public void resolve(Ticket ticket) {

        throw new IllegalStateException(
                "Um chamado aberto precisa ser iniciado antes de ser resolvido."
        );
    }

    @Override
    public void reopen(Ticket ticket) {

        throw new IllegalStateException(
                "O chamado ja esta aberto."
        );
    }
}