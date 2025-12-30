package com.example.ORM_project.controller;

import com.example.ORM_project.dto.QuestionRequestDto;
import com.example.ORM_project.dto.QuestionResponseDto;
import com.example.ORM_project.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponseDto> create(@RequestBody QuestionRequestDto request) {
        QuestionResponseDto createdQuestion = questionService.createQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getAll() {
        List<QuestionResponseDto> categories = questionService.getAllQuestion();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> getById(@PathVariable Long id) {
        QuestionResponseDto Question = questionService.getQuestionById(id);
        return ResponseEntity.ok(Question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> update(@PathVariable Long id, @RequestBody QuestionRequestDto request) {
        QuestionResponseDto updatedQuestion = questionService.updateQuestion(id, request);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>("Question " + id + " успешно удален", HttpStatus.OK);
    }
}
