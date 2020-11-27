package com.example.demo.controller;

import com.example.demo.model.Birthday;
import com.example.demo.service.BirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BirthdayController {

    @Autowired
    private BirthdayService birthdayService;

    @GetMapping("/birthdays")
    private List<Birthday> getAllBirthdays() {
        return birthdayService.getAllBirthdays();
    }

//    @GetMapping("/birthdays/{id}")
//    private Birthday getBirthdays(@PathVariable("id") int id) {
//        return birthdayService.getBirthdaysById(id);
//    }
//
//    @DeleteMapping("/birthdays/{id}")
//    private void deleteBirthday(@PathVariable("id") int id) {
//        birthdayService.delete(id);
//    }

//    @PostMapping("/birthdays")
//    private int saveBirthday(@RequestBody Birthday birthday) {
//        birthdayService.saveOrUpdate(birthday);
//        return birthday.getId();
//    }
}