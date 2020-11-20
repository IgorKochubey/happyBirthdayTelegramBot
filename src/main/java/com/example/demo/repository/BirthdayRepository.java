package com.example.demo.repository;

import com.example.demo.model.Birthday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthdayRepository extends JpaRepository<Birthday, Integer> {
}
