package com.example.demo.schedulers;

import com.example.demo.Bot;
import com.example.demo.model.Birthday;
import com.example.demo.service.BirthdayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class BirthdayCreationChatSchedule {
    @Autowired
    private BirthdayService birthdayService;
    @Autowired
    private Bot bot;

    @Scheduled(cron = "0 45 12 * * ?", zone = "Europe/Moscow") //Единичный запуск каждый день в 12:45:00 по МСК
    public void scheduleFixedDelayTask() {
        LocalDate nextBirthDate = LocalDate.now()
                .withYear(1900)
                .plusDays(3);

        List<Birthday> byBirthdayDates = birthdayService.findByBirthdayDate(nextBirthDate);
        for (Birthday birthday : byBirthdayDates) {
            List<Long> responsibleUserIds = birthdayService.getResponsibleUsersByChat(birthday.getChatId());
            for (Long userId : responsibleUserIds) {
                bot.sendMsg(userId, "\uD83C\uDF89 After 3 days, should send congratulations to " + birthday.getUserName() + "\uD83E\uDD73");
            }
        }
    }
}
