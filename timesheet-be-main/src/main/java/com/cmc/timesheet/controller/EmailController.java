package com.cmc.timesheet.controller;

import com.cmc.timesheet.entity.EmailEntity;
import com.cmc.timesheet.model.request.EmailRequest;
import com.cmc.timesheet.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailController {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/send-mail")
    public ResponseEntity<?> sendMail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        Integer res = emailService.sendMail(emailRequest);
        return ResponseEntity.ok(res);
    }

    // Sending email with attachment
    @PostMapping("/send-mail-with-attachment")
    public String sendMailWithAttachment(
            @RequestBody EmailEntity details)
    {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }
}
