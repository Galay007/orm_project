package com.example.ORM_project.controller;

import com.example.ORM_project.dto.CourseRequestDto;
import com.example.ORM_project.dto.CourseResponseDto;
import com.example.ORM_project.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/course")
public class CourseController {


    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDto> create(@RequestBody CourseRequestDto request) {
        CourseResponseDto createdCourse = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAll() {
        List<CourseResponseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getById(@PathVariable Long id) {
        CourseResponseDto course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> update(@PathVariable Long id, @RequestBody CourseRequestDto request) {
        CourseResponseDto updatedCourse = courseService.updateCourse(id, request);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>("Course " + id + " успешно удален", HttpStatus.OK);
    }
}
