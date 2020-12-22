package com.example.demo;

import com.example.demo.model.Birthday;
import com.example.demo.service.BirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private BirthdayService birthdayService;

    /**
     * Метод для приема сообщений.
     *
     * @param update Содержит сообщение от пользователя.
     */

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (isNull(message)) {
            return;
        }
        String text = message.getText();
        if (isEmpty(text)) {
            return;
        }
        User from = message.getFrom();
        Long chatId = message.getChatId();
        Integer userID = from.getId();

        if (text.contains("/setBirthday")) {
            System.out.println("userID = " + userID);
            System.out.println("chatId = " + chatId);

            String maybeDate = text.replace("/setBirthday", "").trim();
            try {
                LocalDate localDate = LocalDate.parse(maybeDate + "-" + LocalDate.now().getYear(), DateTimeFormatter.ofPattern("dd-MM-uuuu"));
                Birthday birthday = new Birthday(chatId, userID, localDate);
                birthdayService.saveOrUpdate(birthday);
            } catch (DateTimeParseException e) {
                text = "Use example: /setBirthday 31-12";
            }
        }

        if (text.contains("/setResponsible")) {
            System.out.println("chatId = " + chatId);

            String maybeResponsible = text.replace("/setResponsible", "").trim();
            try {
                boolean isResponsible = Boolean.parseBoolean(maybeResponsible);
                Birthday birthdayByUserIdAndChatId = birthdayService.getBirthdayByUserIdAndChatId(userID, chatId);
                if (nonNull(birthdayByUserIdAndChatId)) {
                    birthdayByUserIdAndChatId.setResponsible(isResponsible);

                    long countResponsibleOfChat = birthdayService.getCountResponsibleOfChat(chatId);
                    if (countResponsibleOfChat > 2) {
                        System.out.println("countResponsibleOfChat = " + countResponsibleOfChat);
                        return;
                    }
                    birthdayService.saveOrUpdate(birthdayByUserIdAndChatId);
                }
            } catch (DateTimeParseException e) {
                text = "Use example: /setResponsible true";
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::onUpdateReceived);
    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     *
     * @return имя бота
     */

    @Override
    public String getBotUsername() {
        return "HappyBirthdayChatBot";
    }


    /**
     * Метод возвращает token бота для связи с сервером Telegram
     *
     * @return token для бота
     */

//    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotToken() {
        return "1425765610:AAFpRNJJjgxAc3qHqY2amljOVsZFWzixyMw";//botToken;
    }
}