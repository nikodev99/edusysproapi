package com.edusyspro.api.mail;

public interface EmailSender {
    void send(EmailRequest request) throws Exception;
}
