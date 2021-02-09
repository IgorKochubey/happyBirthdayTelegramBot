package com.example.demo.repository;

import com.example.demo.model.Birthday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BirthdayRepository extends CrudRepository<Birthday, Integer> {
    Birthday findByUserIdAndChatId(Long userId, long chatId);

    @Override
    List<Birthday> findAll();

    long countByChatIdAndResponsibleIsTrue(long chatId);

    List<Birthday> findByBirthdayDate(LocalDate birthdayDate);

    List<Birthday> findByChatIdAndResponsibleTrue(Long chatId);
}
