package com.rafael.supportapi.patterns.strategy;

import com.rafael.supportapi.models.*;
import org.springframework.stereotype.Component;

@Component
public class PriorityStrategySelector {

    public PriorityStrategy select(
            TicketImpact impact,
            TicketUrgency urgency
    ) {

        if (impact == TicketImpact.HIGH
                || urgency == TicketUrgency.HIGH) {

            return new HighPriorityStrategy();
        }

        if (impact == TicketImpact.MEDIUM
                || urgency == TicketUrgency.MEDIUM) {

            return new MediumPriorityStrategy();
        }

        return new LowPriorityStrategy();
    }
}