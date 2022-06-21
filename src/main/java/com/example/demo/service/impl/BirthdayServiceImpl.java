package com.example.demo.service.impl;

import com.example.demo.model.Birthday;
import com.example.demo.repository.BirthdayRepository;
import com.example.demo.service.BirthdayService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@Transactional
public class BirthdayServiceImpl implements BirthdayService {

    private final BirthdayRepository birthdayRepository;

    public BirthdayServiceImpl(BirthdayRepository birthdayRepository) {
        this.birthdayRepository = birthdayRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Birthday> getAllBirthdays() {
        return birthdayRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Birthday getBirthdayByUserIdAndChatId(Long userId, long chatId) {
        return birthdayRepository.findByUserIdAndChatId(userId, chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistBirthdayByUserIdAndChatId(Long userId, long chatId) {
        Birthday byUserIdAndChatId = birthdayRepository.findByUserIdAndChatId(userId, chatId);
        return nonNull(byUserIdAndChatId);
    }

    @Override
    public void saveOrUpdate(Birthday birthday) {
        birthdayRepository.save(birthday);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountResponsibleOfChat(Long chatId) {
        return birthdayRepository.countByChatIdAndResponsibleIsTrue(chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Birthday> findByBirthdayDate(LocalDate birthdayDate) {
        return birthdayRepository.findByBirthdayDate(birthdayDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getResponsibleUsersByChat(Long chatId) {
        List<Birthday> byChatIdAndResponsibleTrue = birthdayRepository.findByChatIdAndResponsibleTrue(chatId);
        return CollectionUtils.emptyIfNull(byChatIdAndResponsibleTrue).stream()
                .map(Birthday::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Long chatId, Long userId) {
        birthdayRepository.deleteBirthdayByChatIdAndUserId(chatId, userId);
    }
}