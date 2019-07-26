package com.pravah.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pravah.web.dao.TicketsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArray;
import java.util.Map;

@Service
public class TicketService {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private TicketsDao ticketsDao;
    public JsonNode searchTickets(Map<String,String[]> searchParams){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", ticketsDao.searchTickets(searchParams));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    public JsonNode bulkAddTicket(ArrayNode tickets){
        ObjectNode response = mapper.createObjectNode();
            for (JsonNode ticket : tickets) {
                addTicket(ticket);
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

    public JsonNode deleteTicket(Map<String,String[]> searchParams){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", ticketsDao.deleteTickets(searchParams));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    public JsonNode getCountBy(Map<String,String[]> searchParams){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", transformCountResponse(ticketsDao.getCountBy(searchParams)));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    private JsonNode transformCountResponse(JsonNode aggregationResponse){
        ArrayNode aggregationResponseArray= (ArrayNode)aggregationResponse;
        ArrayNode tranformedResponse= mapper.createArrayNode();
        for(JsonNode aggregationResponseNode: aggregationResponseArray){
            ObjectNode aggrgationResponseobj= (ObjectNode) aggregationResponseNode;
            aggrgationResponseobj.fields().forEachRemaining(x->addNode(tranformedResponse,x.getKey(),x.getValue()));
        }
        return tranformedResponse;
    }
    private void  addNode(ArrayNode transformedResponse, String key, JsonNode val){
        ObjectNode objectNode= mapper.createObjectNode();
        objectNode.put("key",key);
        objectNode.put("val",val);
        transformedResponse.add(objectNode);
    }

    public JsonNode update(JsonNode ticket){
        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", ticketsDao.update(ticket));
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
