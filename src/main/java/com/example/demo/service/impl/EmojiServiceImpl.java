package com.example.demo.service.impl;

import com.example.demo.model.Emoji;
import com.example.demo.service.EmojiService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
public class EmojiServiceImpl implements EmojiService {
    private static final String EMOJI_JSON_RESOURCE = "emoji.json";
    private static final String EMOJI = "emoji";
    private static final String LETTER = "letter";
    private static final String FLAG = "flag";
    private static final String TAGS = "tags";
    private static final String EMOJI_CHAR = "emojiChar";
    private static final int EMOJI_COUNT = 3;

    @Value("classpath:" + EMOJI_JSON_RESOURCE)
    private Resource resource;

    private List<Emoji> emojis;

    @PostConstruct
    public void loadEmojis() {
        emojis = loadEmojisByResource();
    }

    @Override
    public String getRandomEmoji() {
        return IntStream.range(0, EMOJI_COUNT)
                .map(i -> getRandomNumber())
                .mapToObj(randomNumber -> emojis.get(randomNumber).getEmoji())
                .collect(Collectors.joining(" "));
    }

    private int getRandomNumber() {
        int maximum = emojis.size();
        int minimum = 0;
        Random rn = new Random();
        int range = maximum - minimum + 1;
        return rn.nextInt(range) + minimum;
    }

    private List<Emoji> loadEmojisByResource() {
        JSONArray emojisJSON = new JSONArray(inputStreamToString());
        return IntStream.range(0, emojisJSON.length())
                .mapToObj(i -> buildEmojiFromJSON(emojisJSON.getJSONObject(i)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private String inputStreamToString() {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Emoji buildEmojiFromJSON(JSONObject json) {
        if (!json.has(EMOJI)) {
            return null;
        }

        List<String> tags = jsonArrayToStringList(json.getJSONArray(TAGS));
        if (isNotEmpty(tags) && (tags.contains(FLAG) || tags.contains(LETTER))) {
            return null;
        }

        String emojiValue = json.getString(EMOJI);
        String emojiChar = json.getString(EMOJI_CHAR);
        return new Emoji(emojiValue, emojiChar, tags);
    }

    private List<String> jsonArrayToStringList(JSONArray array) {
        return IntStream.range(0, array.length())
                .mapToObj(array::getString)
                .collect(Collectors.toList());
    }
}
