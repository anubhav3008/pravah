package com.pravah.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.pravah.web.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;
    @RequestMapping( method = RequestMethod.GET)
    public JsonNode searchTickets(HttpServletRequest httpServletRequest){
        return ticketService.searchTickets(httpServletRequest.getParameterMap());
    }

    @RequestMapping(path = "/add")
    public JsonNode addTicket(@RequestBody JsonNode ticket){
        return ticketService.addTicket(ticket);
    }

    @RequestMapping( method = RequestMethod.DELETE)
    public JsonNode deleteTicket(HttpServletRequest httpServletRequest){
        return ticketService.deleteTicket(httpServletRequest.getParameterMap());
    }
    @RequestMapping(path = "/bulkadd")
    public JsonNode bulkaddTicket(@RequestBody ArrayNode tickets){
        return ticketService.bulkAddTicket(tickets);
    }

    @RequestMapping(path = "/count")
    public JsonNode getCountBy(@RequestParam("field") String field){
        return ticketService.getCountBy(field);
    }

}
