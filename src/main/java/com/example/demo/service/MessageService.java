package com.example.demo.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface MessageService {
    SendMessage doCallback(Update update);

    Optional<SendMessage> doMessage(Update update);
}
