package com.example.ORM_project.controller;

import com.example.ORM_project.dto.AnswerOptionRequestDto;
import com.example.ORM_project.dto.AnswerOptionResponseDto;
import com.example.ORM_project.service.AnswerOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/answer-option")
public class AnswerOptionController {

    private final AnswerOptionService AnswerOptionService;

    @PostMapping
    public ResponseEntity<AnswerOptionResponseDto> create(@RequestBody AnswerOptionRequestDto request) {
        AnswerOptionResponseDto createdAnswerOption = AnswerOptionService.createAnswerOption(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerOption);
    }

    @GetMapping
    public ResponseEntity<List<AnswerOptionResponseDto>> getAll() {
        List<AnswerOptionResponseDto> categories = AnswerOptionService.getAllAnswerOption();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerOptionResponseDto> getById(@PathVariable Long id) {
        AnswerOptionResponseDto AnswerOption = AnswerOptionService.getAnswerOptionById(id);
        return ResponseEntity.ok(AnswerOption);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerOptionResponseDto> update(@PathVariable Long id, @RequestBody AnswerOptionRequestDto request) {
        AnswerOptionResponseDto updatedAnswerOption = AnswerOptionService.updateAnswerOption(id, request);
        return ResponseEntity.ok(updatedAnswerOption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        AnswerOptionService.deleteAnswerOption(id);
        return new ResponseEntity<>("AnswerOption " + id + " успешно удален", HttpStatus.OK);
    }
}
