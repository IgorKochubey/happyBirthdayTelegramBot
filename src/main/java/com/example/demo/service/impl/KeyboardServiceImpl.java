package com.example.demo.service.impl;

import com.example.demo.factory.SendMessageFactory;
import com.example.demo.service.KeyboardService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
        return sendMessageFactory.createSendMessage(chatId, "Choose month:")
                .setReplyMarkup(inlineKeyboardMarkup);
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
        return sendMessageFactory.createSendMessage(chatId, "Choose day:")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @Override
    public SendMessage sendInlineKeyBoardMessageConfirmation(long chatId) {
        List<InlineKeyboardButton> keyboardButtonsRow = Stream.of(YES, NO)
                .map(this::getInlineKeyboardButton)
                .collect(Collectors.toList());

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(new ArrayList<>(Collections.singletonList(keyboardButtonsRow)));
        return sendMessageFactory.createSendMessage(chatId, "Save:")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    private InlineKeyboardButton getInlineKeyboardButton(String name) {
        return new InlineKeyboardButton()
                .setText(name)
                .setCallbackData(name);
    }
}
