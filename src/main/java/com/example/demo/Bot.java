package com.example.demo;

import com.example.demo.model.Birthday;
import com.example.demo.service.BirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
                sendMsg(chatId, text);
            }
        }

        if (text.contains("/setResponsible")) {
            System.out.println("chatId = " + chatId);

                boolean isResponsible = true;
                Birthday birthdayByUserIdAndChatId = birthdayService.getBirthdayByUserIdAndChatId(userID, chatId);
                if (nonNull(birthdayByUserIdAndChatId) && !birthdayByUserIdAndChatId.isResponsible()) {
                    birthdayByUserIdAndChatId.setResponsible(isResponsible);

                    long countResponsibleOfChat = birthdayService.getCountResponsibleOfChat(chatId);
                    if (countResponsibleOfChat >= 2) {
                        text = "Sorry, but this chat has 2 responsible users now";
                        sendMsg(chatId, text);
                        return;
                    }
                    birthdayService.saveOrUpdate(birthdayByUserIdAndChatId);
                }

        }

        if (text.contains("/unsetResponsible")) {
            System.out.println("chatId = " + chatId);

                boolean isResponsible = false;
                Birthday birthdayByUserIdAndChatId = birthdayService.getBirthdayByUserIdAndChatId(userID, chatId);
                if (nonNull(birthdayByUserIdAndChatId) && birthdayByUserIdAndChatId.isResponsible()) {
                    birthdayByUserIdAndChatId.setResponsible(isResponsible);

                    long countResponsibleOfChat = birthdayService.getCountResponsibleOfChat(chatId);
                    if (countResponsibleOfChat != 2) {
                        text = "Sorry, but this chat should has 2 responsible users";
                        sendMsg(chatId, text);
                        return;
                    }
                    birthdayService.saveOrUpdate(birthdayByUserIdAndChatId);
                }

        }
    }

    /**
     * Метод для настройки сообщения и его отправки.
     *
     * @param chatId id чата
     * @param s      Строка, которую необходимот отправить в качестве сообщения.
     */

    private synchronized void sendMsg(Long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
//            log.log(Level.SEVERE, "Exception: ", e.toString());
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