package com.rafael.supportapi.patterns.strategy;

import com.rafael.supportapi.models.*;

public interface PriorityStrategy {

    TicketPriority calculate(
            TicketImpact impact,
            TicketUrgency urgency
    );
}