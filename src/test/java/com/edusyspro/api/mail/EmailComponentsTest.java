package com.edusyspro.api.mail;

import com.edusyspro.api.helper.log.L;
import com.edusyspro.api.mail.queue.EmailProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmailComponentsTest {
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailProducer emailProducer;

    @Test
    public void sendHelloWorldEmailTest() {
        EmailRequest emailRequest = sampleRequest();
        boolean hasBeenSent = false;
        try {
            emailService.send(emailRequest);
            hasBeenSent = true;
        }catch (Exception e){
            L.error(e.getMessage());
        }
        System.out.println("-------------------------");
        System.out.println("Email sent status: " + hasBeenSent);
        System.out.println("-------------------------");

        assertTrue(hasBeenSent);
    }

    @Test
    public void testEnqueueMailSender() {
        EmailRequest emailRequest = sampleRequest();
        boolean hasBeenSent = false;

        try {
            emailProducer.enqueue(emailRequest);
            hasBeenSent = true;
        }catch (Exception e){
            L.error(e.getMessage());
        }

        System.out.println("-------------------------");
        System.out.println("Email sent status: " + hasBeenSent);
        System.out.println("-------------------------");

        assertTrue(hasBeenSent);
    }

    private EmailRequest sampleRequest() {
        return EmailRequest.builder()
                .subject("Test subject")
                .from("no-reply@edusyspro.com")
                .to(List.of("nikhe.niama99@gmail.com", "nikhe.niama99@outlook.fr"))
                .htmlBody("<p>Hello <b>world</b></p>")
                .textBody("Hello World")
                .attachments(Map.of("sample.txt", "hello world".getBytes()))
                .build();
    }
}
