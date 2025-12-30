package com.example.ORM_project.controller;

import com.example.ORM_project.dto.EnrollmentRequestDto;
import com.example.ORM_project.dto.EnrollmentResponseDto;
import com.example.ORM_project.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> create(@RequestBody EnrollmentRequestDto request) {
        EnrollmentResponseDto createdEnrollment = enrollmentService.createEnrollment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnrollment);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> getAll() {
        List<EnrollmentResponseDto> Enrollments = enrollmentService.getAllEnrollment();
        return ResponseEntity.ok(Enrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto> getById(@PathVariable Long id) {
        EnrollmentResponseDto Enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(Enrollment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto> update(@PathVariable Long id, @RequestBody EnrollmentRequestDto request) {
        EnrollmentResponseDto updatedEnrollment = enrollmentService.updateEnrollment(id, request);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return new ResponseEntity<>("Enrollment " + id + " успешно удален", HttpStatus.OK);
    }
}
