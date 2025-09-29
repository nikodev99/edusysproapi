package com.edusyspro.api.mail.queue;

import com.edusyspro.api.helper.log.L;
import com.edusyspro.api.mail.EmailRequest;
import com.edusyspro.api.mail.EmailSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    private final EmailSender emailSender;

    public EmailConsumer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "${app.mail.queue.queue}")
    public void handleEmailQueue(EmailRequest request) {
        L.info("Received email request from queue. Subject: {}, To: {}", request.getSubject(), request.getTo());
        try {
            emailSender.send(request);
            L.info("Successfully sent email. Subject: {}", request.getSubject());
            //TODO: persist status, metrics, move to DLQ on repeated failure
        }catch (Exception e){
            L.error("Failed to send email. Subject: {}, Recipients: {}", request.getSubject(), request.getTo(), e);
            throw new RuntimeException("failed to send email job", e);
        }
    }
}
