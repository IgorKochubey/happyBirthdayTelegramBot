package com.example.demo.strategy.message.impl;

import com.example.demo.facade.BotFacade;
import com.example.demo.factory.SendMessageFactory;
import com.example.demo.strategy.message.MessageStrategy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class TryLuckMessageStrategy implements MessageStrategy {
    private final SendMessageFactory sendMessageFactory;
    private final BotFacade botFacade;

    public TryLuckMessageStrategy(SendMessageFactory sendMessageFactory, BotFacade botFacade) {
        this.sendMessageFactory = sendMessageFactory;
        this.botFacade = botFacade;
    }

    @Override
    public SendMessage getSendMessage(User user, Long chatId) {
        return sendMessageFactory.createSendMessage(chatId, botFacade.getRandomEmoji());
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return message.equals("/try_luck");
    }
}
