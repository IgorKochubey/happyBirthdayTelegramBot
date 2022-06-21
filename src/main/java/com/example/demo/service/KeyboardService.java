package com.example.demo.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.time.Month;

public interface KeyboardService {
    String YES = "Yes";
    String NO = "No";

    SendMessage sendInlineKeyBoardMessageMonth(long chatId);

    SendMessage sendInlineKeyBoardMessageDay(long chatId, Month month);

    SendMessage sendInlineKeyBoardMessageConfirmation(long chatId);

    ReplyKeyboardMarkup setButtons();
}
