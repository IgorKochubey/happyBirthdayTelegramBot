package com.example.demo.strategy.message.impl;

import com.example.demo.facade.BotFacade;
import com.example.demo.factory.SendMessageFactory;
import com.example.demo.strategy.message.MessageStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Service
public class SetResponsibleBirthdayMessageStrategy implements MessageStrategy {
    private final SendMessageFactory sendMessageFactory;
    private final BotFacade botFacade;

    public SetResponsibleBirthdayMessageStrategy(SendMessageFactory sendMessageFactory, BotFacade botFacade) {
        this.sendMessageFactory = sendMessageFactory;
        this.botFacade = botFacade;
    }

    @Transactional
    @Override
    public Optional<SendMessage> getSendMessage(User user, Long chatId) {
        Long userId = user.getId();
        String responsibleMessage = botFacade.setResponsibleBirthday(chatId, userId);
        SendMessage sendMessage = sendMessageFactory.createSendMessage(chatId, responsibleMessage);
        return Optional.of(sendMessage);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return message.equals("/set_responsible_birthday");
    }
}
