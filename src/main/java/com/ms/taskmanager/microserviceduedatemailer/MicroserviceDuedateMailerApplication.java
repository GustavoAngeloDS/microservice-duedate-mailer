package com.ms.taskmanager.microserviceduedatemailer;

import com.ms.taskmanager.microserviceduedatemailer.service.CronNotifyPendingNotifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceDuedateMailerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceDuedateMailerApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        cronNotifyPendingNotifications.findAndNotifyNext30MinutesDueDateTasks();
    }

    @Autowired
    CronNotifyPendingNotifications cronNotifyPendingNotifications;
}
