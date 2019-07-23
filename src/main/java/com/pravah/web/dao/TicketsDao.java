package com.pravah.web.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pravah.web.entity.History;
import com.pravah.web.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class TicketsDao {

    public List<Ticket> getAssignedTickets( String name){

        return getDummyTickets();
    }

    public List<Ticket> getLoggedTickets( String name){

        return getDummyTickets();
    }
    public List<Ticket> getAllTickets( String name){

        return getDummyTickets();
    }
    public JsonNode addTicket( JsonNode ticket){

        ObjectNode ticket1 = (ObjectNode) ticket;
        ticket1.put( "id",(int)(Math.random()*1000));
        return ticket;
    }

    private List<Ticket> getDummyTickets(){

        List<History> histories= new ArrayList<>();
        histories.add(new History(new Date(),"created a tender for the contract", "radheyshyam tiwari"));
        histories.add(new History(new Date(),"abc corp assigned the tender", "radheyshyam tiwari"));
        Ticket ticket1=  new Ticket(null,"FootPath broken",
                "A big time issue to padestrians. Acccident prone for me",
                "Municipal corporation",
                "anubhav shrivastava",
                new Date(),
                new Date(),histories);

        Ticket ticket2=  new Ticket(null,"Road jam mostly",
                "A big time issue to car owners",
                "Municipal corporation",
                "mashood",
                new Date(),
                new Date(),histories);

        return Arrays.asList(ticket1,ticket2);
    }
}
