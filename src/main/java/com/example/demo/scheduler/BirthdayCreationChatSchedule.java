package com.example.demo.scheduler;

import com.example.demo.Bot;
import com.example.demo.factory.SendMessageFactory;
import com.example.demo.model.Birthday;
import com.example.demo.service.BirthdayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.util.List;

import static com.example.demo.facade.BotFacade.COUNT_OF_RESPONSIBLE_USERS_IN_CHAT;
import static java.lang.String.format;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Slf4j
@Component
public class BirthdayCreationChatSchedule {
    private static final String CONGRATULATIONS_MESSAGE = "\uD83C\uDF89 After 3 days, should send congratulations to %s \uD83E\uDD73";
    private static final String PLEASE_CHAT_WITH = " please chat with %s";

    private final BirthdayService birthdayService;
    private final Bot bot;
    private final SendMessageFactory sendMessageFactory;

    public BirthdayCreationChatSchedule(BirthdayService birthdayService, Bot bot, SendMessageFactory sendMessageFactory) {
        this.birthdayService = birthdayService;
        this.bot = bot;
        this.sendMessageFactory = sendMessageFactory;
    }

    @Scheduled(cron = "0 45 12 * * ?", zone = "Europe/Moscow") //Единичный запуск каждый день в 12:45:00 по МСК
    public void scheduleFixedDelayTask() {
        LocalDate nextBirthDate = LocalDate.now()
                .withYear(1900)
                .plusDays(3);

        List<Birthday> byBirthdayDates = birthdayService.findByBirthdayDate(nextBirthDate);
        for (Birthday birthday : byBirthdayDates) {
            List<Long> users = birthdayService.getResponsibleUsersByChat(birthday.getChatId());
            if (isEmpty(users)) {
                break;
            }
            Long chatIdFirst = users.get(0);
            if (users.size() == COUNT_OF_RESPONSIBLE_USERS_IN_CHAT - 1) {
                String message = format(CONGRATULATIONS_MESSAGE, birthday.getUserName());
                sendMessage(chatIdFirst, message);
            }
            if (users.size() == COUNT_OF_RESPONSIBLE_USERS_IN_CHAT) {
                Long chatIdSecond = users.get(1);

                String messageFirst = format(CONGRATULATIONS_MESSAGE, birthday.getUserName()) + format(PLEASE_CHAT_WITH, chatIdSecond);
                sendMessage(chatIdFirst, messageFirst);

                String messageSecond = format(CONGRATULATIONS_MESSAGE, birthday.getUserName()) + format(PLEASE_CHAT_WITH, chatIdFirst);
                sendMessage(chatIdSecond, messageSecond);
            }
        }
    }

    private void sendMessage(long chatId, String message) {
        SendMessage sendMessage = sendMessageFactory.createSendMessage(chatId, message);
        bot.executeMessage(sendMessage);
    }
}
