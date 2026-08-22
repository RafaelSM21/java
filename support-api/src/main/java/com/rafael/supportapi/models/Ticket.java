package com.rafael.supportapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketImpact impact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketUrgency urgency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    protected Ticket() {
    }

    public Ticket(
            String title,
            String description,
            TicketType type,
            TicketPriority priority,
            TicketImpact impact,
            TicketUrgency urgency
    ) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.impact = impact;
        this.urgency = urgency;
        this.status = TicketStatus.OPEN;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketType getType() {
        return type;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public TicketImpact getImpact() {
        return impact;
    }

    public TicketUrgency getUrgency() {
        return urgency;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}