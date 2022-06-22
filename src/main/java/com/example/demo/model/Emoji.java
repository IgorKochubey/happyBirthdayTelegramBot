package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class Emoji {
    private String emoji;
    private String emojiChar;
    private String description;
    private boolean supportsFitzpatrick;
    private List<String> aliases;
    private List<String> tags;
    private String unicode;
    private String htmlDec;
    private String htmlHex;

    public Emoji(String emoji, String emojiChar, List<String> tags) {
        this.emoji = emoji;
        this.emojiChar = emojiChar;
        this.tags = tags;
    }
}
