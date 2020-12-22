package com.example.demo.service;

import com.example.demo.model.Birthday;
import com.example.demo.repository.BirthdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BirthdayService {

    @Autowired
    private BirthdayRepository birthdayRepository;

    public List<Birthday> getAllBirthdays() {
        return birthdayRepository.findAll();
    }

    public Birthday getBirthdayByUserIdAndChatId(int userId, long chatId) {
        return birthdayRepository.findByUserIdAndChatId(userId, chatId);
    }

    public void saveOrUpdate(Birthday birthday) {
        birthdayRepository.save(birthday);
    }

    public void delete(int id) {
        birthdayRepository.deleteById(id);
    }

    public long getCountResponsibleOfChat(Long chatId) {
        Birthday example = new Birthday();
        example.setChatId(chatId);
        example.setResponsible(true);

        return birthdayRepository.count(Example.of(example));
    }
}