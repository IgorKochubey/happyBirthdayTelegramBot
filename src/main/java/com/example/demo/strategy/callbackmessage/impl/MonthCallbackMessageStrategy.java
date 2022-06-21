package com.example.demo.strategy.callbackmessage.impl;

import com.example.demo.service.KeyboardService;
import com.example.demo.strategy.callbackmessage.CallbackMessageStrategy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthCallbackMessageStrategy implements CallbackMessageStrategy {
    private final List<String> MONTH_VALUES = Arrays.stream(Month.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    private final KeyboardService keyboardService;

    public MonthCallbackMessageStrategy(KeyboardService keyboardService) {
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage getSendMessage(User user, Long chatId, String message){
        Long userId = new Long(user.getId());
        USER_BIRTHDAY_CACHE.put(userId, message);
        Month month = Month.valueOf(message);
        return keyboardService.sendInlineKeyBoardMessageDay(chatId, month);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return MONTH_VALUES.contains(message);
    }
}
