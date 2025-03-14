package com.example.demo.facade.impl;

import com.example.demo.factory.SendMessageFactory;
import com.example.demo.model.Birthday;
import com.example.demo.service.BirthdayService;
import com.example.demo.facade.BotFacade;
import com.example.demo.service.EmojiService;
import com.example.demo.service.KeyboardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.service.impl.KeyboardServiceImpl.CHOOSE_MONTH;
import static com.example.demo.strategy.callbackmessage.CallbackMessageStrategy.USER_BIRTHDAY_CACHE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@Transactional
public class BotFacadeImpl implements BotFacade {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu");

    private final BirthdayService birthdayService;
    private final EmojiService emojiService;
    private final KeyboardService keyboardService;
    private final SendMessageFactory sendMessageFactory;

    public BotFacadeImpl(BirthdayService birthdayService, EmojiService emojiService, KeyboardService keyboardService, SendMessageFactory sendMessageFactory) {
        this.birthdayService = birthdayService;
        this.emojiService = emojiService;
        this.keyboardService = keyboardService;
        this.sendMessageFactory = sendMessageFactory;
    }

    @Override
    public String unsetResponsibleBirthday(Long chatId, Long userId) {
        Birthday birthdayByUserIdAndChatId = birthdayService.getBirthdayByUserIdAndChatId(userId, chatId);
        if (isNull(birthdayByUserIdAndChatId)) {
            return "Sorry, but the user's birthday was not found for this chat";
        }

        if (isFalse(birthdayByUserIdAndChatId.isResponsible())) {
            return "Sorry, but you are not responsible for this chat";
        }

        long countResponsibleOfChat = birthdayService.getCountResponsibleOfChat(chatId);
        if (countResponsibleOfChat != COUNT_OF_RESPONSIBLE_USERS_IN_CHAT) {
            return "Sorry, but this chat should has " + COUNT_OF_RESPONSIBLE_USERS_IN_CHAT + " responsible users";
        }

        birthdayByUserIdAndChatId.setResponsible(false);
        birthdayService.saveOrUpdate(birthdayByUserIdAndChatId);
        return "Now you are not responsible for this chat";
    }

    @Override
    public String setResponsibleBirthday(Long chatId, Long userId) {
        long countResponsibleOfChat = birthdayService.getCountResponsibleOfChat(chatId);
        if (countResponsibleOfChat >= COUNT_OF_RESPONSIBLE_USERS_IN_CHAT) {
            return "Sorry, but this chat already contains " + COUNT_OF_RESPONSIBLE_USERS_IN_CHAT + " responsible users";
        }

        Birthday birthdayByUserIdAndChatId = birthdayService.getBirthdayByUserIdAndChatId(userId, chatId);
        if (isNull(birthdayByUserIdAndChatId)) {
            return "Sorry, but the user's birthday was not found for this chat";
        }

        if (birthdayByUserIdAndChatId.isResponsible()) {
            return "Sorry, but you are already responsible";
        }

        birthdayByUserIdAndChatId.setResponsible(true);
        birthdayService.saveOrUpdate(birthdayByUserIdAndChatId);
        return "Congratulation, you are responsible for this chat";
    }

    @Override
    public String deleteBirthday(Long chatId, Long userId) {
        Birthday birthdayByUserIdAndChatId = birthdayService.getBirthdayByUserIdAndChatId(userId, chatId);
        if (isNull(birthdayByUserIdAndChatId)) {
            return "Sorry, but the user's birthday was not found for this chat";
        }

        if (birthdayByUserIdAndChatId.isResponsible()) {
            return "Sorry, but you are responsible for this chat";
        }

        birthdayService.remove(chatId, userId);
        return "Your birthday row was deleted";
    }

    @Override
    public void createNewBirthday(User user, Long chatId, String birthdayDate) {
        String firstName = StringUtils.defaultString(user.getFirstName());
        String lastName = StringUtils.defaultString(user.getLastName());
        String nickName = StringUtils.defaultString(user.getUserName());

        List<String> names = Arrays.asList(firstName, lastName, nickName);
        String fullName = names.stream()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(" "));

        String[] split = birthdayDate.split("-");
        String day = split[0];
        Month month = Month.valueOf(split[1]);
        String monthValue = String.valueOf(month.getValue());
        LocalDate localDate = LocalDate.parse(formatDate(day) + "-" + formatDate(monthValue) + "-" + 1900, DATE_TIME_FORMATTER);
        Birthday birthday = new Birthday(chatId, user.getId(), localDate, fullName);
        birthdayService.saveOrUpdate(birthday);
    }

    private String formatDate(String value) {
        if (value.length() == 1) {
            return "0" + value;
        }
        return value;
    }

    @Override
    public List<Birthday> getAllBirthdays() {
        return birthdayService.getAllBirthdays();
    }

    @Override
    public Optional<SendMessage> getCreateBirthdayMessage(Long chatId, Long userId) {
        boolean exists = birthdayService.isExistBirthdayByUserIdAndChatId(userId, chatId);
        if (exists) {
            SendMessage sendMessage = sendMessageFactory.createSendMessage(chatId, "Your birthday for this chat already exist");
            return Optional.of(sendMessage);
        } else if (USER_BIRTHDAY_CACHE.containsKey(userId)) {
            return Optional.empty();
        } else {
            USER_BIRTHDAY_CACHE.put(userId, CHOOSE_MONTH);
            SendMessage sendMessage = keyboardService.sendInlineKeyBoardMessageMonth(chatId);
            return Optional.of(sendMessage);
        }
    }

    @Override
    public String getRandomEmoji() {
        return emojiService.getRandomEmoji();
    }

}
