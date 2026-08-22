package com.rafael.supportapi.patterns.factory;

import com.rafael.supportapi.dto.CreateTicketRequest;
import com.rafael.supportapi.models.Ticket;
import com.rafael.supportapi.models.TicketPriority;
import com.rafael.supportapi.models.TicketType;
import com.rafael.supportapi.patterns.strategy.PriorityStrategy;

public class IncidentTicketCreator extends TicketCreator {

    public IncidentTicketCreator(PriorityStrategy priorityStrategy) {
        super(priorityStrategy);
    }

    @Override
    public Ticket create(CreateTicketRequest request) {

        TicketPriority priority =
                priorityStrategy.calculate(
                        request.impact(),
                        request.urgency()
                );

        return new Ticket(
                request.title(),
                request.description(),
                TicketType.INCIDENT,
                priority,
                request.impact(),
                request.urgency()
        );
    }
}