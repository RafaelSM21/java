package com.rafael.supportapi.services;

import com.rafael.supportapi.dto.CreateTicketRequest;
import com.rafael.supportapi.dto.TicketResponse;
import com.rafael.supportapi.models.Ticket;
import com.rafael.supportapi.patterns.factory.IncidentTicketCreator;
import com.rafael.supportapi.patterns.factory.RequestTicketCreator;
import com.rafael.supportapi.patterns.factory.TicketCreator;
import com.rafael.supportapi.patterns.state.TicketStateContext;
import com.rafael.supportapi.patterns.strategy.PriorityStrategySelector;
import com.rafael.supportapi.repositories.TicketRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final PriorityStrategySelector strategySelector;

    public TicketService(
            TicketRepository ticketRepository,
            PriorityStrategySelector strategySelector
    ) {
        this.ticketRepository = ticketRepository;
        this.strategySelector = strategySelector;
    }

    public TicketResponse create(CreateTicketRequest request) {

        var strategy = strategySelector.select(
                request.impact(),
                request.urgency()
        );

        TicketCreator creator = switch (request.type()) {

            case INCIDENT -> new IncidentTicketCreator(strategy);

            case REQUEST -> new RequestTicketCreator(strategy);
        };

        Ticket ticket = creator.create(request);

        Ticket savedTicket = ticketRepository.save(ticket);

        return TicketResponse.from(savedTicket);
    }

    public List<TicketResponse> findAll() {

        return ticketRepository.findAll()
                .stream()
                .map(TicketResponse::from)
                .toList();
    }

    public TicketResponse findById(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Chamado nao encontrado.")
                );

        return TicketResponse.from(ticket);
    }

    public TicketResponse start(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Chamado nao encontrado.")
                );

        TicketStateContext context = new TicketStateContext(ticket);
        context.start(ticket);

        ticketRepository.save(ticket);

        return TicketResponse.from(ticket);
    }

    public TicketResponse resolve(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Chamado nao encontrado.")
                );

        TicketStateContext context = new TicketStateContext(ticket);
        context.resolve(ticket);

        ticketRepository.save(ticket);

        return TicketResponse.from(ticket);
    }

    public TicketResponse reopen(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Chamado nao encontrado.")
                );

        TicketStateContext context = new TicketStateContext(ticket);
        context.reopen(ticket);

        ticketRepository.save(ticket);

        return TicketResponse.from(ticket);
    }
}