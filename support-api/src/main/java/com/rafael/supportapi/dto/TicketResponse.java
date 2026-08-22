package com.rafael.supportapi.dto;

import com.rafael.supportapi.models.*;

public record TicketResponse(

        Long id,

        String title,

        String description,

        TicketType type,

        TicketPriority priority,

        TicketImpact impact,

        TicketUrgency urgency,

        TicketStatus status
) {

    public static TicketResponse from(Ticket ticket) {

        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getType(),
                ticket.getPriority(),
                ticket.getImpact(),
                ticket.getUrgency(),
                ticket.getStatus()
        );
    }
}