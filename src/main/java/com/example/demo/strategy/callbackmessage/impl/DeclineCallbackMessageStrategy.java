package com.example.demo.strategy.callbackmessage.impl;

import com.example.demo.factory.SendMessageFactory;
import com.example.demo.strategy.callbackmessage.CallbackMessageStrategy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.example.demo.service.KeyboardService.NO;

@Service
public class DeclineCallbackMessageStrategy implements CallbackMessageStrategy {
    private final SendMessageFactory sendMessageFactory;

    public DeclineCallbackMessageStrategy(SendMessageFactory sendMessageFactory) {
        this.sendMessageFactory = sendMessageFactory;
    }

    @Override
    public SendMessage getSendMessage(User user, Long chatId, String message) {
        Long userId = new Long(user.getId());
        USER_BIRTHDAY_CACHE.remove(userId);
        return sendMessageFactory.createSendMessage(chatId, "Not saved");
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return NO.contains(message);
    }
}
