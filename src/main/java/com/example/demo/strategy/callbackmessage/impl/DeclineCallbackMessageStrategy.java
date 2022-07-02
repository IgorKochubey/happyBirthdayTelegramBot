package com.example.demo.strategy.callbackmessage.impl;

import com.example.demo.factory.SendMessageFactory;
import com.example.demo.strategy.callbackmessage.CallbackMessageStrategy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

import static com.example.demo.service.KeyboardService.NO;

@Service
public class DeclineCallbackMessageStrategy implements CallbackMessageStrategy {
    private final SendMessageFactory sendMessageFactory;

    public DeclineCallbackMessageStrategy(SendMessageFactory sendMessageFactory) {
        this.sendMessageFactory = sendMessageFactory;
    }

    @Override
    public Optional<SendMessage> getSendMessage(User user, Long chatId, String message) {
        Long userId = user.getId();
        USER_BIRTHDAY_CACHE.remove(userId);
        SendMessage notSaved = sendMessageFactory.createSendMessage(chatId, "Not saved");
        return Optional.of(notSaved);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return NO.contains(message);
    }
}
