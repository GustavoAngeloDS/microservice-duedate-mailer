package com.ms.taskmanager.microserviceduedatemailer.service;

import com.ms.taskmanager.microserviceduedatemailer.constants.ConstantQueries;
import com.ms.taskmanager.microserviceduedatemailer.dto.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CronNotifyPendingNotifications {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronNotifyPendingNotifications.class);

    private JdbcTemplate jdbcTemplate;
    private RabbitMqService rabbitMqService;

    @Autowired
    public CronNotifyPendingNotifications(JdbcTemplate jdbcTemplate, RabbitMqService rabbitMqService) {
        this.jdbcTemplate = jdbcTemplate;
        this.rabbitMqService = rabbitMqService;
        jdbcTemplate.execute(ConstantQueries.SET_TIMEZONE);
    }

    @Scheduled(cron = "0 0/30 * * * *") // Every 30 minutes
    public void findAndNotifyNext30MinutesDueDateTasks() {
        LOGGER.debug("Finding next 30 minutes due dates");
        jdbcTemplate.query(ConstantQueries.FIND_30MIN_PENDING_NOTIFICATIONS, (rs, rowNumber) ->
            new NotificationDto(
                    rs.getString("emailTo"),
                    rs.getString("subject"),
                    rs.getString("text")
            )
        ).forEach(notificationDto -> rabbitMqService.sendMessage((notificationDto)));
    }
}