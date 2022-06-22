package com.example.demo.factory.impl;

import com.example.demo.factory.SendMessageFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class SendMessageFactoryImpl implements SendMessageFactory {
    @Override
    public SendMessage createSendMessage(long chatId, String s) {
        return new SendMessage()
                .setChatId(chatId)
                .enableMarkdown(true)
                .setText(s);
    }
}
