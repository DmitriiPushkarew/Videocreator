package com.example.videocreator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${videocreator.rabbit.exchange}")
    private String exchangeName;

    @Value("${videocreator.rabbit.queue}")
    private String queueName;

    @Value("${videocreator.rabbit.routingKey}")
    private String routingKey;

    @Bean
    public DirectExchange fileLinkExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue fileLinkQueue() {
        return new Queue(queueName, true, false, false);
    }

    @Bean
    public Binding fileLinkBinding(Queue fileQueue, DirectExchange fileLinksExchange) {
        return BindingBuilder.bind(fileQueue).to(fileLinksExchange).with(routingKey);
    }
}