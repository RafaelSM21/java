package com.rafael.supportapi.patterns.strategy;

import com.rafael.supportapi.models.*;

public class LowPriorityStrategy implements PriorityStrategy {

    @Override
    public TicketPriority calculate(
            TicketImpact impact,
            TicketUrgency urgency
    ) {

        return TicketPriority.LOW;
    }
}