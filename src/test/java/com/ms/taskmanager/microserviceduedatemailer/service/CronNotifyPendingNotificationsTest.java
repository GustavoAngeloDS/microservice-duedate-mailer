package com.ms.taskmanager.microserviceduedatemailer.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class CronNotifyPendingNotificationsTest {

    @BeforeEach
    void configureMocks() {
        openMocks(this);
    }

    @Test
    void checkMocksIteraction() {
        final String EXPECTED_SET = "SET TIMEZONE TO 'America/Sao_Paulo'";
        final String EXPECTED_SELECT = "SELECT\n" +
                "    u.email as emailTo,\n" +
                "    nc.message text,\n" +
                "    nc.title subject\n" +
                "FROM\n" +
                "    delivery_date dd INNER JOIN tasks t on\n" +
                "        dd.id = t.delivery_date_id\n" +
                "    INNER JOIN notification_configuration nc on\n" +
                "        t.notification_configuration_id = nc.id\n" +
                "    INNER JOIN task_users tu on\n" +
                "        t.id = tu.task_id\n" +
                "    INNER JOIN users u on\n" +
                "        tu.user_id = u.id\n" +
                "WHERE\n" +
                "    dd.active = true \n" +
                "AND dd.accomplished = false \n" +
                "AND nc.notification_type =  'EMAIL' \n" +
                "AND to_char(dd.date, 'YYYY-MM-DD') = to_char(current_timestamp, 'YYYY-MM-DD') \n" +
                "AND dd.time = to_char(current_timestamp + interval '30 minutes', 'HH24:MI')";

        cronNotifyPendingNotifications.findAndNotifyTasksThatWillBeOverdueOnNext30Minutes();

        verify(jdbcTemplate, times(1)).execute(stringArgumentCaptor1.capture());
        verify(jdbcTemplate, times(1)).query(stringArgumentCaptor2.capture(), any(RowMapper.class));

        assertEquals(stringArgumentCaptor1.getValue(), EXPECTED_SET);
        assertEquals(stringArgumentCaptor2.getValue(), EXPECTED_SELECT);
    }

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    CronNotifyPendingNotifications cronNotifyPendingNotifications;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor1;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor2;

    @Captor
    ArgumentCaptor<Object> objectArgumentCaptor;
}