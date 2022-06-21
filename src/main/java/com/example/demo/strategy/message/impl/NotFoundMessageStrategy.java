package com.example.demo.strategy.message.impl;

import com.example.demo.strategy.message.MessageStrategy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

public class NotFoundMessageStrategy implements MessageStrategy {
    @Override
    public SendMessage getSendMessage(User user, Long chatId) {
        return null;
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return false;
    }
}
