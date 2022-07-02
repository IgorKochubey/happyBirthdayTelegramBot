package com.example.demo.strategy.callbackmessage.impl;

import com.example.demo.facade.BotFacade;
import com.example.demo.factory.SendMessageFactory;
import com.example.demo.strategy.callbackmessage.CallbackMessageStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

import static com.example.demo.service.KeyboardService.YES;

@Service
public class ApproveCallbackMessageStrategy implements CallbackMessageStrategy {
    private final SendMessageFactory sendMessageFactory;
    private final BotFacade botFacade;

    public ApproveCallbackMessageStrategy(SendMessageFactory sendMessageFactory, BotFacade botFacade) {
        this.sendMessageFactory = sendMessageFactory;
        this.botFacade = botFacade;
    }

    @Transactional
    @Override
    public Optional<SendMessage> getSendMessage(User user, Long chatId, String message) {
        Long userId = user.getId();
        String birthdayDate = USER_BIRTHDAY_CACHE.get(userId);
        botFacade.createNewBirthday(user, chatId, birthdayDate);

        USER_BIRTHDAY_CACHE.remove(userId);
        SendMessage saved = sendMessageFactory.createSendMessage(chatId, "Saved");
        return Optional.of(saved);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return YES.contains(message);
    }
}
