package com.pravah.web.utils;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmailUtil {

    public void sendEmail(String fromMail, List<String> toMails, String html, String subjectText) throws IOException {

        System.out.println("going to send mail to ="+toMails);
        Email from = new Email(fromMail,"MGTC");
        String subject = subjectText;
        Content content = new Content("text/html", html);
        Mail mail =  new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);
        for(String toMail: toMails) {
            Email to = new Email(toMail);
            Personalization personalization = new Personalization();
            personalization.addTo(to);
            mail.addPersonalization(personalization);
        }
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
           throw ex;
        }


    }



}
