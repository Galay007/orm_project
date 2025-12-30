package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    boolean existsByDescription(String description);
}