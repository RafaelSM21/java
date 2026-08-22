package com.rafael.supportapi.controllers;

import com.rafael.supportapi.dto.CreateTicketRequest;
import com.rafael.supportapi.dto.TicketResponse;
import com.rafael.supportapi.services.TicketService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse create(
            @Valid @RequestBody CreateTicketRequest request
    ) {

        return ticketService.create(request);
    }

    @GetMapping
    public List<TicketResponse> findAll() {

        return ticketService.findAll();
    }

    @GetMapping("/{id}")
    public TicketResponse findById(
            @PathVariable Long id
    ) {

        return ticketService.findById(id);
    }

    @PatchMapping("/{id}/start")
    public TicketResponse start(
            @PathVariable Long id
    ) {

        return ticketService.start(id);
    }

    @PatchMapping("/{id}/resolve")
    public TicketResponse resolve(
            @PathVariable Long id
    ) {

        return ticketService.resolve(id);
    }

    @PatchMapping("/{id}/reopen")
    public TicketResponse reopen(
            @PathVariable Long id
    ) {

        return ticketService.reopen(id);
    }
}