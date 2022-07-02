package com.example.demo.facade;

import com.example.demo.model.Birthday;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.Optional;

public interface BotFacade {
    int COUNT_OF_RESPONSIBLE_USERS_IN_CHAT = 2;

    String unsetResponsibleBirthday(Long chatId, Long userId);

    String setResponsibleBirthday(Long chatId, Long userId);

    String deleteBirthday(Long chatId, Long userId);

    String getRandomEmoji();

    void createNewBirthday(User user, Long chatId, String birthdayDate);

    List<Birthday> getAllBirthdays();

    SendMessage getCreateBirthdayMessage(Long chatId, Long userId);
}
