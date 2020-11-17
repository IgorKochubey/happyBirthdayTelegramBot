package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class Bot extends TelegramLongPollingBot {


    /**
     * Метод для приема сообщений.
     *
     * @param update Содержит сообщение от пользователя.
     */

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(isNull(message)){
            return;
        }
        String text = message.getText();
        if(isEmpty(text)){
            return;
        }
        User from = message.getFrom();
        Long chatId = message.getChatId();

        if(text.contains("/setBirthday")){
            String maybeDate = text.replace("/setBirthday", "").trim();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
            try {
                Date parse = sdf.parse(maybeDate);
                System.out.println("parse = " + parse);
            } catch (ParseException e) {
                text = "Use example: /setBirthday 31-12";
            }
            Integer userID = from.getId();
            System.out.println("maybeDate = " + maybeDate);
            System.out.println("userID = " + userID);
            System.out.println("chatId = " + chatId);
        }
        sendMsg(chatId.toString(), text);
    }


    /**
     * Метод для настройки сообщения и его отправки.
     *
     * @param chatId id чата
     * @param s      Строка, которую необходимот отправить в качестве сообщения.
     */

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
//            log.log(Level.SEVERE, "Exception: ", e.toString());
        }
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

    @Value("${bot_token}")
    private String botToken;

    @Override
    public String getBotToken() {
        return "1425765610:AAFpRNJJjgxAc3qHqY2amljOVsZFWzixyMw";//botToken;
    }
}