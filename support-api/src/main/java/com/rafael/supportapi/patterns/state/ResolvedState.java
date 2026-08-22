package com.rafael.supportapi.patterns.state;

import com.rafael.supportapi.models.Ticket;
import com.rafael.supportapi.models.TicketStatus;

public class ResolvedState implements TicketState {

    @Override
    public void start(Ticket ticket) {

        throw new IllegalStateException(
                "Um chamado resolvido precisa ser reaberto primeiro."
        );
    }

    @Override
    public void resolve(Ticket ticket) {

        throw new IllegalStateException(
                "O chamado ja esta resolvido."
        );
    }

    @Override
    public void reopen(Ticket ticket) {

        ticket.setStatus(TicketStatus.OPEN);
    }
}