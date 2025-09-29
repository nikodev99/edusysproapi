package com.edusyspro.api.mail.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class RabbitMQConfig {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange mailExchange(Environment environment) {
        return new DirectExchange(environment.getProperty("app.mail.queue.exchange"));
    }

    @Bean
    public Queue mailQueue(Environment environment) {
        return new Queue(Objects.requireNonNull(environment.getProperty("app.mail.queue.queue")), true);
    }

    @Bean
    public Binding binding(Queue mailQueue, DirectExchange mailExchange, Environment environment) {
        return BindingBuilder.bind(mailQueue)
                .to(mailExchange)
                .with(environment.getProperty("app.mail.queue.routing-key"));
    }
}
