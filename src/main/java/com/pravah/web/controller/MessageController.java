package com.pravah.web.controller;

import com.pravah.web.service.AgendaService;
import com.pravah.web.service.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/communication")
public class MessageController {

    @Autowired
    EmailService emailService;

    @Autowired
    AgendaService agendaService;
    public @ResponseBody
    @RequestMapping("/email")
    JsonNode sendEmail(@RequestParam("meetingId") int meetingId,
                       @RequestParam(name = "emailId", required = false) List<String> emailIds) throws Exception {
        return emailService.sendEmail(meetingId, emailIds);

    }

    @RequestMapping(value="/agenda/{id}", method=RequestMethod.GET)
    public ResponseEntity<byte[]> getAgenda(@PathVariable("id") int meetingId) throws Exception {
        byte[] contents=agendaService.getAgenda(meetingId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "Agenda"+meetingId+".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
}
