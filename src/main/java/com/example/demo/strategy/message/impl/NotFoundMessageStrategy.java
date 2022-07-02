package com.example.demo.strategy.message.impl;

import com.example.demo.strategy.message.MessageStrategy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

public class NotFoundMessageStrategy implements MessageStrategy {
    @Override
    public Optional<SendMessage> getSendMessage(User user, Long chatId) {
        return Optional.empty();
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return false;
    }
}
