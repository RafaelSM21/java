package com.rafael.supportapi.dto;

import com.rafael.supportapi.models.TicketImpact;
import com.rafael.supportapi.models.TicketType;
import com.rafael.supportapi.models.TicketUrgency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTicketRequest(

        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        TicketType type,

        @NotNull
        TicketImpact impact,

        @NotNull
        TicketUrgency urgency
) {
}