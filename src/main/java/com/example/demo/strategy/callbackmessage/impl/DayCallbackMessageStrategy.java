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
import java.util.stream.IntStream;

@Service
public class DayCallbackMessageStrategy implements CallbackMessageStrategy {
    private final List<String> DAYS_VALUES = IntStream.range(1, 32)
            .boxed()
            .map(String::valueOf)
            .collect(Collectors.toList());

    private final List<String> MONTH_VALUES = Arrays.stream(Month.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    private final KeyboardService keyboardService;

    public DayCallbackMessageStrategy(KeyboardService keyboardService) {
        this.keyboardService = keyboardService;
    }

    @Override
    public Optional<SendMessage> getSendMessage(User user, Long chatId, String message) {
        Long userId = user.getId();
        String birthdayMonthStr = USER_BIRTHDAY_CACHE.get(userId);

        if (!MONTH_VALUES.contains(birthdayMonthStr)) {
            return Optional.empty();
        }

        USER_BIRTHDAY_CACHE.put(userId, message + "-" + birthdayMonthStr);
        SendMessage sendMessage = keyboardService.sendInlineKeyBoardMessageConfirmation(chatId);
        return Optional.of(sendMessage);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return DAYS_VALUES.contains(message);
    }
}
