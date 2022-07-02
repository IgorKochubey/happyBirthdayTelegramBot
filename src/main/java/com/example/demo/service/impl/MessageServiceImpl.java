package com.example.demo.service.impl;

import com.example.demo.service.MessageService;
import com.example.demo.strategy.callbackmessage.CallbackMessageStrategy;
import com.example.demo.strategy.callbackmessage.impl.NotFoundCallbackMessageStrategy;
import com.example.demo.strategy.message.MessageStrategy;
import com.example.demo.strategy.message.impl.NotFoundMessageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    private final List<CallbackMessageStrategy> callbackMessageStrategies;
    private final List<MessageStrategy> messageStrategies;

    public MessageServiceImpl(List<CallbackMessageStrategy> callbackMessageStrategies, List<MessageStrategy> messageStrategies) {
        this.callbackMessageStrategies = callbackMessageStrategies;
        this.messageStrategies = messageStrategies;
    }

    @Value("${bot.name}")
    private String botName;

    @Override
    public SendMessage doCallback(Update update) {
        String message = replaceMessage(update.getCallbackQuery().getData());
        User user = update.getCallbackQuery().getFrom();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        return callbackMessageStrategies.stream()
                .filter(strategy -> strategy.isMessageStrategyType(message))
                .findFirst()
                .orElseGet(NotFoundCallbackMessageStrategy::new)
                .getSendMessage(user, chatId, message);
    }

    @Override
    public Optional<SendMessage> doMessage(Update update) {
        Message message = update.getMessage();
        if (!message.hasText()) {
            return Optional.empty();
        }

        Long chatId = message.getChatId();
        User user = message.getFrom();
        String text = replaceMessage(message.getText());

        return messageStrategies.stream()
                .filter(strategy -> strategy.isMessageStrategyType(text))
                .findFirst()
                .orElseGet(NotFoundMessageStrategy::new)
                .getSendMessage(user, chatId);
    }

    private String replaceMessage(String data) {
        if (isEmpty(data)) {
            return null;
        }
        return data.replace("@" + botName, EMPTY);
    }
}
