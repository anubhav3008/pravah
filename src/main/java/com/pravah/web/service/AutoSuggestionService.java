package com.pravah.web.service;

import com.pravah.web.dao.AutosuggestionDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class AutoSuggestionService {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired  AutosuggestionDao autosuggestionDao;

    public JsonNode getStaticValue(){

        ObjectNode response = mapper.createObjectNode();
        try {
            response.putPOJO("data", autosuggestionDao.getStaticValue());
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
