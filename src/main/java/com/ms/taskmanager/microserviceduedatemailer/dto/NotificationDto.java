package com.ms.taskmanager.microserviceduedatemailer.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDto {
    private String emailTo;
    private String subject;
    private String text;
}