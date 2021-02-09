package com.example.demo.controller;

import com.example.demo.model.Birthday;
import com.example.demo.schedulers.BirthdayCreationChatSchedule;
import com.example.demo.service.BirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BirthdayController {

    @Autowired
    private BirthdayService birthdayService;

    @Autowired
    private BirthdayCreationChatSchedule schedule;

    @GetMapping("/birthdays")
    private List<Birthday> getAllBirthdays() {
        return birthdayService.getAllBirthdays();
    }

    @PostMapping("/runScheduler")
    private void runScheduler() {
        schedule.scheduleFixedDelayTask();
    }
}