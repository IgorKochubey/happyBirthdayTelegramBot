package com.example.demo.factory.impl;

import com.example.demo.factory.SendMessageFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class SendMessageFactoryImpl implements SendMessageFactory {
    @Override
    public SendMessage createSendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setText(text);
        return sendMessage;
    }
}
