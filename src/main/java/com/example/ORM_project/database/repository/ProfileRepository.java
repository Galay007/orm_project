package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
