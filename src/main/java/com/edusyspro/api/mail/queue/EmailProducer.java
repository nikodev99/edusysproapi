package com.edusyspro.api.mail.queue;

import com.edusyspro.api.mail.EmailRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Environment environment;


    public EmailProducer(RabbitTemplate rabbitTemplate, Environment environment) {
        this.rabbitTemplate = rabbitTemplate;
        this.environment = environment;
    }

    public void enqueue(EmailRequest request) {
        rabbitTemplate.convertAndSend(
                Objects.requireNonNull(environment.getProperty("app.mail.queue.exchange")),
                Objects.requireNonNull(environment.getProperty("app.mail.queue.routing-key")),
                request
        );
    }
}
