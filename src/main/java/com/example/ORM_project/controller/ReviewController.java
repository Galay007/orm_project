package com.example.ORM_project.controller;

import com.example.ORM_project.dto.ReviewRequestDto;
import com.example.ORM_project.dto.ReviewResponseDto;
import com.example.ORM_project.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@RequestBody ReviewRequestDto request) {
        ReviewResponseDto createdReview = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAll() {
        List<ReviewResponseDto> Reviews = reviewService.getAllReview();
        return ResponseEntity.ok(Reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getById(@PathVariable Long id) {
        ReviewResponseDto Review = reviewService.getReviewById(id);
        return ResponseEntity.ok(Review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> update(@PathVariable Long id, @RequestBody ReviewRequestDto request) {
        ReviewResponseDto updatedReview = reviewService.updateReview(id, request);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>("Review " + id + " успешно удален", HttpStatus.OK);
    }
}
