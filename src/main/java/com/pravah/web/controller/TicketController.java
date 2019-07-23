package com.pravah.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.pravah.web.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;
    @RequestMapping(path = "/{user_name}/assigned")
    public JsonNode getAssignedTickets(@PathVariable(value = "name") String name){
        return ticketService.getAssignedTickets(name);
    }
}
