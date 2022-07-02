package com.example.demo.strategy.callbackmessage.impl;

import com.example.demo.service.KeyboardService;
import com.example.demo.strategy.callbackmessage.CallbackMessageStrategy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.service.impl.KeyboardServiceImpl.CHOOSE_MONTH;

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
    public Optional<SendMessage> getSendMessage(User user, Long chatId, String message) {
        Long userId = user.getId();
        String userMessage = USER_BIRTHDAY_CACHE.get(userId);
        if (!CHOOSE_MONTH.equals(userMessage)) {
            return Optional.empty();
        }

        USER_BIRTHDAY_CACHE.put(userId, message);
        Month month = Month.valueOf(message);
        SendMessage sendMessage = keyboardService.sendInlineKeyBoardMessageDay(chatId, month);
        return Optional.of(sendMessage);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return MONTH_VALUES.contains(message);
    }
}
