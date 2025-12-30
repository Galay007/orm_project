package com.example.ORM_project.controller;

import com.example.ORM_project.dto.LessonRequestDto;
import com.example.ORM_project.dto.LessonResponseDto;
import com.example.ORM_project.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    private final LessonService LessonService;

    @PostMapping
    public ResponseEntity<LessonResponseDto> create(@RequestBody LessonRequestDto request) {
        LessonResponseDto createdLesson = LessonService.createLesson(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLesson);
    }

    @GetMapping
    public ResponseEntity<List<LessonResponseDto>> getAll() {
        List<LessonResponseDto> Lessons = LessonService.getAllLesson();
        return ResponseEntity.ok(Lessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDto> getById(@PathVariable Long id) {
        LessonResponseDto Lesson = LessonService.getLessonById(id);
        return ResponseEntity.ok(Lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonResponseDto> update(@PathVariable Long id, @RequestBody LessonRequestDto request) {
        LessonResponseDto updatedLesson = LessonService.updateLesson(id, request);
        return ResponseEntity.ok(updatedLesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        LessonService.deleteLesson(id);
        return new ResponseEntity<>("Lesson " + id + " успешно удален", HttpStatus.OK);
    }
}
