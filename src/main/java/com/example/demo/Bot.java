package com.example.demo;

import com.example.demo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class Bot extends TelegramLongPollingBot {
    private final MessageService messageService;
    private final TelegramBotsApi telegramBotsApi;

    public Bot(MessageService messageService, TelegramBotsApi telegramBotsApi) {
        this.messageService = messageService;
        this.telegramBotsApi = telegramBotsApi;
    }


    @PostConstruct
    public void postConstruct() throws TelegramApiException {
        telegramBotsApi.registerBot(this);
    }

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.name}")
    private String botName;

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     *
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     *
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * Метод для приема сообщений.
     *
     * @param updates Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::onUpdateReceived);
    }

    /**
     * Метод для приема сообщений.
     *
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = null;
        if (update.hasMessage()) {
            sendMessage = messageService.doMessage(update);
        } else if (update.hasCallbackQuery()) {
            sendMessage = messageService.doCallback(update);
        }

        if (nonNull(sendMessage)) {
            executeMessage(sendMessage);
        }
    }

    /**
     * Метод для отправки сообщения.
     *
     * @param sendMessage содержит id чата и текст
     */
    public void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Exception: ", e);
        }
    }
}