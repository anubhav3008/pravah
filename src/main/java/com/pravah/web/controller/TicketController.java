package com.pravah.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.pravah.web.entity.Ticket;
import com.pravah.web.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;
    @RequestMapping(path = "/{user_name}/assigned")
    public JsonNode getAssignedTickets(@PathVariable(value = "user_name") String name){
        return ticketService.getAssignedTickets(name);
    }

    @RequestMapping(path = "/{user_name}/logged")
    public JsonNode getLoggedTickets(@PathVariable(value = "user_name") String name){
        return ticketService.getLoggedTickets(name);
    }

    @RequestMapping(path = "/{user_name}")
    public JsonNode getAllTickets(@PathVariable(value = "user_name") String name){
        return ticketService.getAllTickets(name);
    }

    @RequestMapping(path = "/add")
    public JsonNode addTicket(@RequestBody JsonNode ticket){
        return ticketService.addTicket(ticket);
    }
}
