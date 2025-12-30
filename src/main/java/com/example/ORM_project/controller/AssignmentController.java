package com.example.ORM_project.controller;

import com.example.ORM_project.dto.AssignmentRequestDto;
import com.example.ORM_project.dto.AssignmentResponseDto;
import com.example.ORM_project.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignmentService AssignmentService;

    @PostMapping
    public ResponseEntity<AssignmentResponseDto> create(@RequestBody AssignmentRequestDto request) {
        AssignmentResponseDto createdAssignment = AssignmentService.createAssignment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponseDto>> getAll() {
        List<AssignmentResponseDto> categories = AssignmentService.getAllAssignment();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponseDto> getById(@PathVariable Long id) {
        AssignmentResponseDto Assignment = AssignmentService.getAssignmentById(id);
        return ResponseEntity.ok(Assignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentResponseDto> update(@PathVariable Long id, @RequestBody AssignmentRequestDto request) {
        AssignmentResponseDto updatedAssignment = AssignmentService.updateAssignment(id, request);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        AssignmentService.deleteAssignment(id);
        return new ResponseEntity<>("Assignment " + id + " успешно удален", HttpStatus.OK);
    }
}
