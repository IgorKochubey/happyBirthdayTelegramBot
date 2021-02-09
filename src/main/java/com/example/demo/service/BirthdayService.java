package com.example.demo.service;

import com.example.demo.model.Birthday;
import com.example.demo.repository.BirthdayRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BirthdayService {

    @Autowired
    private BirthdayRepository birthdayRepository;

    public List<Birthday> getAllBirthdays() {
        return birthdayRepository.findAll();
    }

    public Birthday getBirthdayByUserIdAndChatId(Long userId, long chatId) {
        return birthdayRepository.findByUserIdAndChatId(userId, chatId);
    }

    public void saveOrUpdate(Birthday birthday) {
        birthdayRepository.save(birthday);
    }

    public long getCountResponsibleOfChat(Long chatId) {
        return birthdayRepository.countByChatIdAndResponsibleIsTrue(chatId);
    }

    public List<Birthday> findByBirthdayDate(LocalDate birthdayDate) {
        return birthdayRepository.findByBirthdayDate(birthdayDate);
    }

    public List<Long> getResponsibleUsersByChat(Long chatId) {
        List<Birthday> byChatIdAndResponsibleTrue = birthdayRepository.findByChatIdAndResponsibleTrue(chatId);
        return CollectionUtils.emptyIfNull(byChatIdAndResponsibleTrue).stream()
                .map(Birthday::getUserId)
                .collect(Collectors.toList());
    }
}