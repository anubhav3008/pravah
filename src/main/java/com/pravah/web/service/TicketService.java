package com.pravah.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pravah.web.dao.TicketsDao;
import com.pravah.web.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    TicketsDao ticketsDao;
    public JsonNode getAssignedTickets(String userName){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", ticketsDao.getAssignedTickets(userName));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }


    public JsonNode getLoggedTickets(String userName){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", ticketsDao.getLoggedTickets(userName));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    public JsonNode getAllTickets(String userName){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", ticketsDao.getAllTickets(userName));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    public JsonNode addTicket(JsonNode ticket){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", ticketsDao.addTicket(ticket));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
