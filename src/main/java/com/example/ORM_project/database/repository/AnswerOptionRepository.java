package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
    boolean existsByText(String text);
}