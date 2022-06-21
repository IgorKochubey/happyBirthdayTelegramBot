package com.example.demo.controller;

import com.example.demo.facade.BotFacade;
import com.example.demo.model.Birthday;
import com.example.demo.scheduler.BirthdayCreationChatSchedule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BirthdayController {
    private final BirthdayCreationChatSchedule schedule;
    private final BotFacade botFacade;

    public BirthdayController(BirthdayCreationChatSchedule schedule, BotFacade botFacade) {
        this.schedule = schedule;
        this.botFacade = botFacade;
    }

    @GetMapping("/birthdays")
    private List<Birthday> getAllBirthdays() {
        return botFacade.getAllBirthdays();
    }

    @PostMapping("/runScheduler")
    private void runScheduler() {
        schedule.scheduleFixedDelayTask();
    }

    @GetMapping("/smiles")
    private String smiles() {
        return botFacade.getRandomEmoji();
    }
}