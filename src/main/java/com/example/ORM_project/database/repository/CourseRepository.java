package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
