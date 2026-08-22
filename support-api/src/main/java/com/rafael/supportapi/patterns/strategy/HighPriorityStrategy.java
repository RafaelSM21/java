package com.rafael.supportapi.patterns.strategy;

import com.rafael.supportapi.models.*;

public class HighPriorityStrategy implements PriorityStrategy {

    @Override
    public TicketPriority calculate(
            TicketImpact impact,
            TicketUrgency urgency
    ) {

        if (impact == TicketImpact.HIGH
                || urgency == TicketUrgency.HIGH) {

            return TicketPriority.HIGH;
        }

        return TicketPriority.MEDIUM;
    }
}