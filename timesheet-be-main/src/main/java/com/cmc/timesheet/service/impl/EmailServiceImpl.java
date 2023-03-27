package com.cmc.timesheet.service.impl;

import com.cmc.timesheet.entity.EmailEntity;
import com.cmc.timesheet.model.request.EmailRequest;
import com.cmc.timesheet.model.request.TimeSheetDTO;
import com.cmc.timesheet.model.request.TimeSheetInsertRequest;
import com.cmc.timesheet.repository.EmailRepository;
import com.cmc.timesheet.repository.TimeSheetRepository;
import com.cmc.timesheet.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.cmc.timesheet.constants.EmailConstant.*;
import static com.cmc.timesheet.constants.WorkingTypeEnum.NORMAL;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Value("${spring.mail.username}")
    private String sender;

    private static final String templateName = "sendEmail.html";

    public String sendSimpleMail(EmailEntity details)
    {

        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getToMail());
            mailMessage.setText(details.getContent());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String sendMailWithAttachment(EmailEntity details)
    {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getToMail());
            mimeMessageHelper.setText(details.getContent());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
//            FileSystemResource file
//                    = new FileSystemResource(
//                    new File(details.getAttachment()));

//            mimeMessageHelper.addAttachment(
//                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }
    @Override
    public Integer sendMail(EmailRequest emailRequest) {
        LocalDate fromDate = LocalDate.of(emailRequest.getYear(), emailRequest.getMonth(), 1);
        LocalDate toDate = fromDate.with(lastDayOfMonth());
        List<TimeSheetDTO> timeSheetList = timeSheetRepository.getTimeSheetListGroupByUserId(fromDate, toDate, emailRequest.getUserIds(), emailRequest.getIsSendAll());
        if (timeSheetList.size() == 0) return NO_DATA;
        Map<Integer, List<TimeSheetDTO>> listMap = timeSheetList.stream().collect(Collectors.groupingBy(TimeSheetDTO::getUserId));
        for (Integer userId : listMap.keySet()) {
            try {
                // check da gui mail chua
                Integer countIsSentMail = emailRepository.countByUserIdAndMonthAndIsSentIsTrue(userId, emailRequest.getMonth(), emailRequest.getYear());
                if (countIsSentMail > 0) continue;
                // check co bi di muon, ve som
                Integer countAbNormal = timeSheetRepository.countByUserIdAndMonthAndYear(userId, fromDate, toDate, NORMAL.getCode());
                if (countAbNormal == 0) continue;
                sendMailList(userId, emailRequest.getMonth(), emailRequest.getYear(), listMap.get(userId));
            } catch (Exception e) {
                return FAIL;
            }
        }
        return DONE;
    }

    private void sendMailList(Integer userId, Integer month, Integer year, List<TimeSheetDTO> timeSheetList) throws MessagingException {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        // Setting multipart as true for attachments to
        // be send
        mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(timeSheetList.get(0).getEmail());
        mimeMessageHelper.setSubject(timeSheetList.get(0).getLdap() + SUBJECT + month);
        // set content is html
        Locale locale = Locale.forLanguageTag(VI);
        Context context = new Context(locale);
        // set bien
        context.setVariable(MONTH, month);
        context.setVariable(TIMESHEET_LIST, timeSheetList);
        String content = templateEngine.process(templateName, context);

        mimeMessageHelper.setText(content, true);
        // save mail
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setToMail(timeSheetList.get(0).getEmail());
        emailEntity.setSubject(timeSheetList.get(0).getLdap() + SUBJECT + month);
        emailEntity.setContent(content);
        emailEntity.setMonth(month);
        emailEntity.setYear(year);
        emailEntity.setUserId(userId);
        emailEntity.setIsSent(true);
        emailRepository.save(emailEntity);
        // Sending the mail
        javaMailSender.send(mimeMessage);
    }
}
