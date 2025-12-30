package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
