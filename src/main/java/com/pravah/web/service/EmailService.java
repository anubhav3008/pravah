package com.pravah.web.service;

import com.pravah.web.dao.MeetingJsonDao;
import com.pravah.web.dao.UsersDao;
import com.pravah.web.utils.EmailUtil;
import com.pravah.web.utils.ImageHelper;
import com.pravah.web.utils.VelocityUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    MeetingJsonDao meetingJsonDao;
    @Autowired
    VelocityUtil velocityUtil;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    UsersDao usersDao;
    ObjectMapper mapper = new ObjectMapper();
    public JsonNode sendEmail(int meetingId,List<String> emailIds) throws Exception {

        if(emailIds==null || emailIds.isEmpty()){
            emailIds= usersDao.getAllUsers().stream().map(x->x.getEmailId()).collect(Collectors.toList());
        }
        ObjectNode response = mapper.createObjectNode();
        try {
            JsonNode meeting = meetingJsonDao.getMeetingJson(meetingId);
            Map<String, String> images= new HashMap<>();
            images.put("logo",ImageHelper.toastmastersLogoUrl);
            String html=velocityUtil.transform((ObjectNode) mapper.readTree(mapper.writeValueAsString(meeting)),"template/agenda_pdf.vm",images);
            System.out.println("Going to send the mail as ="+html);
            emailUtil.sendEmail("mailer@mgtc.com",emailIds,html,"Meeting Agenda # "+meetingId);

            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;

    }
}
