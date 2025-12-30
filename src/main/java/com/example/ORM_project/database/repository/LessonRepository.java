package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsByTitle(String title);
}