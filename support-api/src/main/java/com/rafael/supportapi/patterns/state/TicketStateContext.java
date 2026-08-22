package com.rafael.supportapi.patterns.state;

import com.rafael.supportapi.models.Ticket;

public class TicketStateContext {

    private final TicketState state;

    public TicketStateContext(Ticket ticket) {

        this.state = switch (ticket.getStatus()) {

            case OPEN -> new OpenState();

            case IN_PROGRESS -> new InProgressState();

            case RESOLVED -> new ResolvedState();
        };
    }

    public void start(Ticket ticket) {
        state.start(ticket);
    }

    public void resolve(Ticket ticket) {
        state.resolve(ticket);
    }

    public void reopen(Ticket ticket) {
        state.reopen(ticket);
    }
}