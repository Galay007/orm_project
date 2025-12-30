package com.example.ORM_project.controller;

import com.example.ORM_project.dto.TestCaseRequestDto;
import com.example.ORM_project.dto.TestCaseResponseDto;
import com.example.ORM_project.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test-case")
public class TestCaseController {
    private final TestCaseService TestCaseService;

    @PostMapping
    public ResponseEntity<TestCaseResponseDto> create(@RequestBody TestCaseRequestDto request) {
        TestCaseResponseDto createdTest = TestCaseService.createTest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTest);
    }

    @GetMapping
    public ResponseEntity<List<TestCaseResponseDto>> getAll() {
        List<TestCaseResponseDto> Tests = TestCaseService.getAllTest();
        return ResponseEntity.ok(Tests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCaseResponseDto> getById(@PathVariable Long id) {
        TestCaseResponseDto Test = TestCaseService.getTestById(id);
        return ResponseEntity.ok(Test);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCaseResponseDto> update(@PathVariable Long id, @RequestBody TestCaseRequestDto request) {
        TestCaseResponseDto updatedTest = TestCaseService.updateTest(id, request);
        return ResponseEntity.ok(updatedTest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        TestCaseService.deleteTest(id);
        return new ResponseEntity<>("TestCase " + id + " успешно удален", HttpStatus.OK);
    }
}
