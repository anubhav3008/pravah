package com.pravah.web.controller;

import com.pravah.web.service.LeaderBoardService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/leaderboard")
public class LeaderBoardController {

    @Autowired
    LeaderBoardService leaderBoardService;
    @RequestMapping("/byName")
    public @ResponseBody JsonNode getLeaderBoardByName(){
        return leaderBoardService.getLeaderBoardByName();
    }
}
