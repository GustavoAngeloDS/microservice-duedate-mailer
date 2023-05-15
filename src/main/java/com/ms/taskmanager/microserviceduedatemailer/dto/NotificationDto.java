package com.ms.taskmanager.microserviceduedatemailer.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDto {
    private UUID id;
    private String emailTo;
    private String subject;
    private String text;
}