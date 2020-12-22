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
    private Integer userId;
    private Long chatId;
    private LocalDate birthdayDate;
    private boolean isResponsible;

    public Birthday() {
    }

    public Birthday(Long chatId, Integer userId, LocalDate birthdayDate) {
        this.chatId = chatId;
        this.userId = userId;
        this.birthdayDate = birthdayDate;
    }
}