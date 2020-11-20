package com.example.demo.service;

import com.example.demo.model.Birthday;
import com.example.demo.repository.BirthdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BirthdayService {

    @Autowired
    private BirthdayRepository birthdayRepository;

    public List<Birthday> getAllBirthdays() {
        return birthdayRepository.findAll();
    }

    public Birthday getBirthdaysById(int id) {
        return birthdayRepository.findById(id)
                .orElseGet(null);
    }

    public void saveOrUpdate(Birthday birthday) {
        birthdayRepository.save(birthday);
    }

    public void delete(int id) {
        birthdayRepository.deleteById(id);
    }
}