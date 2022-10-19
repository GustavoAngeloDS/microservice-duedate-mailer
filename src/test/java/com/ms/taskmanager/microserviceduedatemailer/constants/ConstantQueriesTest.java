package com.ms.taskmanager.microserviceduedatemailer.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantQueriesTest {

    @Test
    void validateSetTimezoneQuery() {
        final String EXPECTED_STRING = "SET TIMEZONE TO 'America/Sao_Paulo'";
        assertEquals(EXPECTED_STRING, ConstantQueries.SET_TIMEZONE);
    }

    @Test
    void validate30MinConstantQuery() {
        final String EXPECTED_STRING = "SELECT\n" +
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

        assertEquals(EXPECTED_STRING, ConstantQueries.FIND_30MIN_PENDING_NOTIFICATIONS);
    }
}