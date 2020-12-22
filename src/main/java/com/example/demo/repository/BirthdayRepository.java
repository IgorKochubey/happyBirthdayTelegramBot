package com.example.demo.repository;

import com.example.demo.model.Birthday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BirthdayRepository extends JpaRepository<Birthday, Integer> {
    @Query("select b from Birthday b where b.userId = :userId and b.chatId = :chatId")
    Birthday findByUserIdAndChatId(@Param("userId") int userId, @Param("chatId") long chatId);
}
