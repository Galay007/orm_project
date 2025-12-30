package com.example.ORM_project.database.repository;

import com.example.ORM_project.database.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    boolean existsByTitle(String title);
}