package com.example.demo.strategy.message.impl;

import com.example.demo.facade.BotFacade;
import com.example.demo.strategy.message.MessageStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class AddBirthdayMessageStrategy implements MessageStrategy {
    private final BotFacade botFacade;

    public AddBirthdayMessageStrategy(BotFacade botFacade) {
        this.botFacade = botFacade;
    }

    @Transactional
    @Override
    public SendMessage getSendMessage(User user, Long chatId) {
        Long userId = Long.valueOf(user.getId());
        return botFacade.getCreateBirthdayMessage(chatId, userId);
    }

    @Override
    public boolean isMessageStrategyType(String message) {
        return message.equals("/add_birthday");
    }
}
