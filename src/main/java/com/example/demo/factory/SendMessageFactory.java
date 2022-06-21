package com.example.demo.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendMessageFactory {
    SendMessage createSendMessage(long chatId, String s);
}
