package com.ms.taskmanager.microserviceduedatemailer.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

    private RabbitTemplate rabbitTemplate;
    private Queue queue;

    @Autowired
    public RabbitMqService(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendMessage(Object objectMessage) {
                    rabbitTemplate.convertAndSend(queue.getName(), objectMessage);

    }
}
