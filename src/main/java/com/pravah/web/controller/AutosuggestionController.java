package com.pravah.web.controller;

import com.pravah.web.service.AutoSuggestionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autosuggest")
public class AutosuggestionController {

    @Autowired AutoSuggestionService autoSuggestionService;
    @RequestMapping(path="/static", method=RequestMethod.GET)
    public @ResponseBody JsonNode getStaticValue(){
        return autoSuggestionService.getStaticValue();
    }


}
