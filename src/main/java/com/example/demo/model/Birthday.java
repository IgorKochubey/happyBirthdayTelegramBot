package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Birthday {

    @Id
    @GeneratedValue
    private int id;
    private Integer userId;
    private Long chatId;
    private Date birthdayDate;

    public Birthday() {
    }

    public Birthday(Long chatId, Integer userId, Date birthdayDate) {
        this.chatId = chatId;
        this.userId = userId;
        this.birthdayDate = birthdayDate;
    }
}