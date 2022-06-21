package com.example.demo;

import com.example.demo.facade.BotFacade;
import com.example.demo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class Bot extends TelegramLongPollingBot {
    private final MessageService messageService;

    public Bot(MessageService messageService) {
        this.messageService = messageService;
    }


    /**
     * Метод для настройки сообщения и его отправки.
     *
     * @param chatId id чата
     * @param s      Строка, которую необходимот отправить в качестве сообщения.
     */

    public synchronized void sendMsg(Long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(s);
        setButtons(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Exception: ", e);
        }
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
        return "1425765610:AAFpRNJJjgxAc3qHqY2amljOVsZFWzixyMw";
    }

    private synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Привет"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Помощь"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
//    private InlineKeyboardMarkup getInline() {
//        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
//        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//        inlineKeyboardButton.setText("Кнопка");
//        inlineKeyboardButton.setCallbackData("17");
//
//        buttons1.add(inlineKeyboardButton);
//        buttons.add(buttons1);
//
//        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
//        markupKeyboard.setKeyboard(buttons);
//        return markupKeyboard;
//    }

    //////////////



    /**
     * Метод для приема сообщений.
     *
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = null;
            if (update.hasMessage()) {
                sendMessage = messageService.doMessage(update);
            } else if (update.hasCallbackQuery()) {
                sendMessage = messageService.doCallback(update);
            }

            if (nonNull(sendMessage)) {
                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            log.error("Exception: ", e);
        }
    }
}