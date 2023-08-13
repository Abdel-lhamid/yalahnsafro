package com.yallahnsafro.yallahnsafrobackend.services.implimentation;
import com.yallahnsafro.yallahnsafrobackend.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    public JavaMailSender emailSender;
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    @Override
    @Async
    public void send(String clientEmail, String subject, String appEmail, String htmlMessage) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            boolean multipart =true;
            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

            message.setContent(htmlMessage, "text/html");
            helper.setFrom(appEmail);
            helper.setSubject(subject);
            helper.setTo(clientEmail);
            this.emailSender.send(message);

        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }



    }
}
