package com.ms.taskmanager.microserviceduedatemailer.service;

import com.ms.taskmanager.microserviceduedatemailer.constants.ConstantQueries;
import com.ms.taskmanager.microserviceduedatemailer.dto.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotifyPendingNotificationsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyPendingNotificationsService.class);

    private JdbcTemplate jdbcTemplate;
    private RabbitMqService rabbitMqService;

    @Autowired
    public NotifyPendingNotificationsService(JdbcTemplate jdbcTemplate, RabbitMqService rabbitMqService) {
        this.jdbcTemplate = jdbcTemplate;
        this.rabbitMqService = rabbitMqService;
        jdbcTemplate.execute(ConstantQueries.SET_TIMEZONE);
    }

//    @Scheduled(cron = "0 0/30 * * * *") // Every 30th minute of the hour
    @Scheduled(cron = "*/10 * * * * *") // Every 10 seconds
    public void findAndNotifyTasksThatWillBeOverdueOnNext30Minutes() {
        LOGGER.debug("Finding next 30 minutes due dates");
        jdbcTemplate.query(ConstantQueries.FIND_30MIN_PENDING_NOTIFICATIONS, (rs, rowNumber) ->
            new NotificationDto(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("emailTo"),
                    rs.getString("subject"),
                    rs.getString("text")
            )
        ).forEach(notificationDto -> {
            rabbitMqService.sendMessage((notificationDto));
            jdbcTemplate.update(ConstantQueries.SET_NOTIFICATION_AS_SENT, notificationDto.getId());
        });
    }
}