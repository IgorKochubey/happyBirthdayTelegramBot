package com.example.demo.strategy.callbackmessage.impl;

import com.example.demo.service.KeyboardService;
import com.example.demo.strategy.callbackmessage.CallbackMessageStrategy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DayCallbackMessageStrategy implements CallbackMessageStrategy {
    private final List<String> DAYS_VALUES = IntStream.range(0, 31)
            .boxed()
            .map(String::valueOf)
            .collect(Collectors.toList());

    private final KeyboardService keyboardService;

    public DayCallbackMessageStrategy(KeyboardService keyboardService) {
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage getSendMessage(User user, Long chatId, String message) {
        Long userId = new Long(user.getId());
        String birthdayMonthStr = USER_BIRTHDAY_CACHE.get(userId);
        USER_BIRTHDAY_CACHE.put(userId, message + "-" + birthdayMonthStr);
        return keyboardService.sendInlineKeyBoardMessageConfirmation(chatId);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return DAYS_VALUES.contains(message);
    }
}
