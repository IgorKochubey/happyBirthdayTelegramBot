package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Birthday {

    @Id
    @GeneratedValue
    private int id;
    private Long userId;
    private String userName;
    private Long chatId;
    private LocalDate birthdayDate;
    private boolean responsible;

    public Birthday() {
    }

    public Birthday(Long chatId, Long userId, LocalDate birthdayDate, String userName) {
        this.chatId = chatId;
        this.userId = userId;
        this.birthdayDate = birthdayDate;
        this.userName = userName;
    }
}