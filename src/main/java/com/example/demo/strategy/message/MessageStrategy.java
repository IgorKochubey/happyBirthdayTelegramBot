package com.example.demo.strategy.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

public interface MessageStrategy {
    Optional<SendMessage> getSendMessage(User user, Long chatId);

    boolean isMessageStrategyType(String message);
}
