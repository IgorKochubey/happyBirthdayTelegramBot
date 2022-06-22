package com.example.demo.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageService {
    SendMessage doCallback(Update update);

    SendMessage doMessage(Update update);
}
