package com.yallahnsafro.yallahnsafrobackend.services;

import javax.mail.MessagingException;

public interface EmailSenderService {
    void send(String clientEmail, String subject, String appEmail, String htmlMessage) throws MessagingException;
}
