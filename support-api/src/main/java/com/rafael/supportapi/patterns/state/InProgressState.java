package com.rafael.supportapi.patterns.state;

import com.rafael.supportapi.models.Ticket;
import com.rafael.supportapi.models.TicketStatus;

public class InProgressState implements TicketState {

    @Override
    public void start(Ticket ticket) {

        throw new IllegalStateException(
                "O chamado ja esta em andamento."
        );
    }

    @Override
    public void resolve(Ticket ticket) {

        ticket.setStatus(TicketStatus.RESOLVED);
    }

    @Override
    public void reopen(Ticket ticket) {

        throw new IllegalStateException(
                "O chamado ainda esta em andamento."
        );
    }
}