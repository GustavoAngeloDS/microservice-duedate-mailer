package com.ms.taskmanager.microserviceduedatemailer.service;

import com.ms.taskmanager.microserviceduedatemailer.constants.ConstantQueries;
import com.ms.taskmanager.microserviceduedatemailer.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.management.NotificationFilter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CronNotifyPendingNotifications {
    private JdbcTemplate jdbcTemplate;
    private RabbitMqService rabbitMqService;

    @Autowired
    public CronNotifyPendingNotifications(JdbcTemplate jdbcTemplate, RabbitMqService rabbitMqService) {
        this.jdbcTemplate = jdbcTemplate;
        this.rabbitMqService = rabbitMqService;
        jdbcTemplate.execute(ConstantQueries.SET_TIMEZONE);
    }

    public void findAndNotifyNext30MinutesDueDateTasks() {
        jdbcTemplate.query(ConstantQueries.FIND_30MIN_PENDING_NOTIFICATIONS, (rs, rowNumber) ->
            new NotificationDto(
                    rs.getString("emailTo"),
                    rs.getString("subject"),
                    rs.getString("text")
            )
        ).forEach(notificationDto -> rabbitMqService.sendMessage((notificationDto)));
    }
}