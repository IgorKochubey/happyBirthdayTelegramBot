package com.example.demo.service.impl;

import com.example.demo.factory.SendMessageFactory;
import com.example.demo.service.KeyboardService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class KeyboardServiceImpl implements KeyboardService {
    private static final int MONTH_IN_ROW = 3;
    private static final int DAY_IN_ROW = 5;
    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int LAST_DAY_OF_MONTH = 1;

    private final SendMessageFactory sendMessageFactory;

    public KeyboardServiceImpl(SendMessageFactory sendMessageFactory) {
        this.sendMessageFactory = sendMessageFactory;
    }

    @Override
    public SendMessage sendInlineKeyBoardMessageMonth(long chatId) {
        List<InlineKeyboardButton> keyboardButtonsRow = Arrays.stream(Month.values())
                .map(value -> getInlineKeyboardButton(value.name()))
                .collect(Collectors.toList());

        List<List<InlineKeyboardButton>> partitions = Lists.partition(keyboardButtonsRow, MONTH_IN_ROW);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(partitions);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        SendMessage sendMessage = sendMessageFactory.createSendMessage(chatId, CHOOSE_MONTH);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage sendInlineKeyBoardMessageDay(long chatId, Month month) {
        int dayOfMonth = month.minLength();

        List<InlineKeyboardButton> keyboardButtonsRow = IntStream.range(FIRST_DAY_OF_MONTH, dayOfMonth + LAST_DAY_OF_MONTH)
                .mapToObj(value -> getInlineKeyboardButton(String.valueOf(value)))
                .collect(Collectors.toList());

        List<List<InlineKeyboardButton>> partitions = Lists.partition(keyboardButtonsRow, DAY_IN_ROW);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(partitions);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        SendMessage sendMessage = sendMessageFactory.createSendMessage(chatId, CHOOSE_DAY);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage sendInlineKeyBoardMessageConfirmation(long chatId) {
        List<InlineKeyboardButton> keyboardButtonsRow = Stream.of(YES, NO)
                .map(this::getInlineKeyboardButton)
                .collect(Collectors.toList());

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(new ArrayList<>(Collections.singletonList(keyboardButtonsRow)));
        SendMessage sendMessage = sendMessageFactory.createSendMessage(chatId, SAVE);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    private InlineKeyboardButton getInlineKeyboardButton(String name) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(name);
        inlineKeyboardButton.setCallbackData(name);
        return inlineKeyboardButton;
    }

    @Override
    public ReplyKeyboardMarkup setButtons() {
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        KeyboardButton hello = new KeyboardButton("Привет");
        keyboardFirstRow.add(hello);

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Помощь"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;

        //sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}
