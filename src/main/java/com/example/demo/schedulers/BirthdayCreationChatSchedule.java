package com.example.demo.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BirthdayCreationChatSchedule {
    @Scheduled(cron = "0 45 12 * * ?", zone = "Europe/Moscow") //Единичный запуск каждый день в 12:45:00 по МСК
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

}
