package com.example.demo.service;

import com.example.demo.model.Birthday;
import java.time.LocalDate;
import java.util.List;

public interface BirthdayService {
    List<Birthday> getAllBirthdays();

    Birthday getBirthdayByUserIdAndChatId(Long userId, long chatId);

    boolean isExistBirthdayByUserIdAndChatId(Long userId, long chatId);

    void saveOrUpdate(Birthday birthday);

    long getCountResponsibleOfChat(Long chatId);

    List<Birthday> findByBirthdayDate(LocalDate birthdayDate);

    List<Long> getResponsibleUsersByChat(Long chatId);

    void remove(Long chatId, Long userId);
}
