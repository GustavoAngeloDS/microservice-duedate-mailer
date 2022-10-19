package com.ms.taskmanager.microserviceduedatemailer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class RabbitMqServiceTest {

    @BeforeEach
    void configMocks() {
        openMocks(this);
    }

    @Test
    void checkMockIteration() {
        when(queue.getName()).thenReturn("test-queue");
        service.sendMessage(new Object());
        verify(rabbitTemplate, times(1)).convertAndSend(stringArgumentCaptor
                .capture(), any(Object.class));

        assertEquals("test-queue", stringArgumentCaptor.getValue());
    }

    @Mock
    Queue queue;

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    RabbitMqService service;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
}