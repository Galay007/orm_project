package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByText(String text);
}