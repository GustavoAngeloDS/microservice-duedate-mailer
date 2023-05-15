package com.ms.taskmanager.microserviceduedatemailer.constants;

public final class ConstantQueries {
    public static final String SET_TIMEZONE = "SET TIMEZONE TO 'America/Sao_Paulo'";

    public static final String SET_NOTIFICATION_AS_SENT = "UPDATE notification_configuration SET sent = " +
            "true WHERE id = ?";

    public static final String FIND_30MIN_PENDING_NOTIFICATIONS =
            "SELECT\n" +
            "    nc.id, \n" +
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
            "AND nc.sent = false  \n" +
            "AND dd.accomplished = false \n" +
            "AND nc.notification_type =  'EMAIL' \n" +
            "AND to_char(dd.date, 'YYYY-MM-DD') = to_char(current_timestamp, 'YYYY-MM-DD') \n" +
            "AND dd.time <= to_char(current_timestamp + interval '30 minutes', 'HH24:MI')";

    private ConstantQueries() {
    }
}
