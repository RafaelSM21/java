package com.rafael.supportapi.patterns.strategy;

import com.rafael.supportapi.models.*;

public class MediumPriorityStrategy implements PriorityStrategy {

    @Override
    public TicketPriority calculate(
            TicketImpact impact,
            TicketUrgency urgency
    ) {

        if (impact == TicketImpact.MEDIUM
                || urgency == TicketUrgency.MEDIUM) {

            return TicketPriority.MEDIUM;
        }

        return TicketPriority.LOW;
    }
}