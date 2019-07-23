package com.pravah.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    ObjectMapper mapper = new ObjectMapper();
    public JsonNode getAssignedTickets(String userName){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", "");
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
