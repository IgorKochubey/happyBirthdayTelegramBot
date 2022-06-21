package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class DemoApplication extends SpringBootServletInitializer {
    private static final String BOT_BEAN_NAME = "bot";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        Bot bot = (Bot) context.getBean(BOT_BEAN_NAME);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            log.error("Exception: ", e);
        }
    }
}
