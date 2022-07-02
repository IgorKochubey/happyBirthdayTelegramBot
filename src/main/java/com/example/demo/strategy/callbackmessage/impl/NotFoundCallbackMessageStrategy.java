package com.example.demo.strategy.callbackmessage.impl;

import com.example.demo.strategy.callbackmessage.CallbackMessageStrategy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

public class NotFoundCallbackMessageStrategy implements CallbackMessageStrategy {
    @Override
    public Optional<SendMessage> getSendMessage(User user, Long chatId, String message) {
        return Optional.empty();
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return false;
    }
}
