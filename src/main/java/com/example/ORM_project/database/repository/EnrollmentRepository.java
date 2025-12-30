package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
