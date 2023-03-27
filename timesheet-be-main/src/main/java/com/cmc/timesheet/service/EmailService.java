package com.cmc.timesheet.service;

import com.cmc.timesheet.entity.EmailEntity;
import com.cmc.timesheet.model.request.EmailRequest;
import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {
    String sendSimpleMail(EmailEntity details);

    String sendMailWithAttachment(EmailEntity details);

    Integer sendMail(EmailRequest emailRequest) throws MessagingException;
}
