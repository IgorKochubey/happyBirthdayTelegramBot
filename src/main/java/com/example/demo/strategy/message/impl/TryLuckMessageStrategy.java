package com.example.demo.strategy.message.impl;

import com.example.demo.facade.BotFacade;
import com.example.demo.factory.SendMessageFactory;
import com.example.demo.strategy.message.MessageStrategy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Service
public class TryLuckMessageStrategy implements MessageStrategy {
    private final SendMessageFactory sendMessageFactory;
    private final BotFacade botFacade;

    public TryLuckMessageStrategy(SendMessageFactory sendMessageFactory, BotFacade botFacade) {
        this.sendMessageFactory = sendMessageFactory;
        this.botFacade = botFacade;
    }

    @Override
    public Optional<SendMessage> getSendMessage(User user, Long chatId) {
        String randomEmoji = botFacade.getRandomEmoji();
        SendMessage sendMessage = sendMessageFactory.createSendMessage(chatId, randomEmoji);
        return Optional.of(sendMessage);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return message.equals("/try_luck");
    }
}
