package com.example.demo.strategy.message.impl;

import com.example.demo.facade.BotFacade;
import com.example.demo.factory.SendMessageFactory;
import com.example.demo.strategy.message.MessageStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class DeleteBirthdayMessageStrategy implements MessageStrategy {
    private final SendMessageFactory sendMessageFactory;
    private final BotFacade botFacade;

    public DeleteBirthdayMessageStrategy(SendMessageFactory sendMessageFactory, BotFacade botFacade) {
        this.sendMessageFactory = sendMessageFactory;
        this.botFacade = botFacade;
    }

    @Transactional
    @Override
    public SendMessage getSendMessage(User user, Long chatId) {
        Long userId = Long.valueOf(user.getId());
        return sendMessageFactory.createSendMessage(chatId, botFacade.deleteBirthday(chatId, userId));
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return message.equals("/delete_birthday");
    }
}
