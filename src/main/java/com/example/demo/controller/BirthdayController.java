package com.example.demo.controller;

import com.example.demo.facade.BotFacade;
import com.example.demo.scheduler.BirthdayCreationChatSchedule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BirthdayController {
    private final BirthdayCreationChatSchedule schedule;
    private final BotFacade botFacade;

    public BirthdayController(BirthdayCreationChatSchedule schedule, BotFacade botFacade) {
        this.schedule = schedule;
        this.botFacade = botFacade;
    }

    @GetMapping("/healthCheck")
    private String healthCheck() {
        return "OK";
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