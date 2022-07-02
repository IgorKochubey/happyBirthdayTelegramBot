package com.example.demo.strategy.callbackmessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public interface CallbackMessageStrategy {
    Map<Long, String> USER_BIRTHDAY_CACHE = new ConcurrentHashMap<>();

    Optional<SendMessage> getSendMessage(User user, Long chatId, String message);

    boolean isMessageStrategyType(String message);
}
