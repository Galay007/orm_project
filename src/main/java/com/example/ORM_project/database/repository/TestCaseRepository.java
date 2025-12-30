package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    boolean existsByTitle(String title);
}