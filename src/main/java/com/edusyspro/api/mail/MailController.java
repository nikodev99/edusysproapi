package com.edusyspro.api.mail;

import com.edusyspro.api.mail.queue.EmailProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final EmailProducer emailProducer;

    public MailController(EmailProducer emailProducer) {
        this.emailProducer = emailProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMail(@RequestBody EmailRequest request) {
        if (request.getTo() == null || request.getTo().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        emailProducer.enqueue(request);
        return ResponseEntity.accepted().build();
    }
}
