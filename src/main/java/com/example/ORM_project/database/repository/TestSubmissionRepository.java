package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.TestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSubmissionRepository extends JpaRepository<TestSubmission, Long> {
}
