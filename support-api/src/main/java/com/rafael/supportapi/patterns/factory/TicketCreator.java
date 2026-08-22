package com.rafael.supportapi.patterns.factory;

import com.rafael.supportapi.dto.CreateTicketRequest;
import com.rafael.supportapi.models.Ticket;
import com.rafael.supportapi.patterns.strategy.PriorityStrategy;

public abstract class TicketCreator {

    protected final PriorityStrategy priorityStrategy;

    protected TicketCreator(PriorityStrategy priorityStrategy) {
        this.priorityStrategy = priorityStrategy;
    }

    /*
     * Factory Method:
     *
     * A classe define o processo geral de criacao,
     * enquanto subclasses decidem qual objeto criar.
     */
    public abstract Ticket create(CreateTicketRequest request);
}