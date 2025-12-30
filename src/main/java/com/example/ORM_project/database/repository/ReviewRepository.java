package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
