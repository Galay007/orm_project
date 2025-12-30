package com.example.ORM_project.controller;

import com.example.ORM_project.dto.SubmissionRequestDto;
import com.example.ORM_project.dto.SubmissionResponseDto;
import com.example.ORM_project.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    private final SubmissionService SubmissionService;

    @PostMapping
    public ResponseEntity<SubmissionResponseDto> create(@RequestBody SubmissionRequestDto request) {
        SubmissionResponseDto createdSubmission = SubmissionService.createSubmission(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubmission);
    }

    @GetMapping
    public ResponseEntity<List<SubmissionResponseDto>> getAll() {
        List<SubmissionResponseDto> categories = SubmissionService.getAllSubmission();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> getById(@PathVariable Long id) {
        SubmissionResponseDto Submission = SubmissionService.getSubmissionById(id);
        return ResponseEntity.ok(Submission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> update(@PathVariable Long id, @RequestBody SubmissionRequestDto request) {
        SubmissionResponseDto updatedSubmission = SubmissionService.updateSubmission(id, request);
        return ResponseEntity.ok(updatedSubmission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        SubmissionService.deleteSubmission(id);
        return new ResponseEntity<>("Submission " + id + " успешно удален", HttpStatus.OK);
    }
}
