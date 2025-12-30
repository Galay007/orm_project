package com.example.ORM_project.controller;

import com.example.ORM_project.dto.TestSubmissionRequestDto;
import com.example.ORM_project.dto.TestSubmissionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test-submission")
public class TestSubmissionController {
    private final com.example.ORM_project.service.TestSubmissionService TestSubmissionService;

    @PostMapping
    public ResponseEntity<TestSubmissionResponseDto> create(@RequestBody TestSubmissionRequestDto request) {
        TestSubmissionResponseDto createdTest = TestSubmissionService.createTest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTest);
    }

    @GetMapping
    public ResponseEntity<List<TestSubmissionResponseDto>> getAll() {
        List<TestSubmissionResponseDto> Tests = TestSubmissionService.getAllTest();
        return ResponseEntity.ok(Tests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestSubmissionResponseDto> getById(@PathVariable Long id) {
        TestSubmissionResponseDto Test = TestSubmissionService.getTestById(id);
        return ResponseEntity.ok(Test);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestSubmissionResponseDto> update(@PathVariable Long id, @RequestBody TestSubmissionRequestDto request) {
        TestSubmissionResponseDto updatedTest = TestSubmissionService.updateTest(id, request);
        return ResponseEntity.ok(updatedTest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        TestSubmissionService.deleteTest(id);
        return new ResponseEntity<>("TestSubmission " + id + " успешно удален", HttpStatus.OK);
    }
}
