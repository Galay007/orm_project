package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Review;
import com.example.ORM_project.database.repository.ReviewRepository;
import com.example.ORM_project.dto.ReviewRequestDto;
import com.example.ORM_project.dto.ReviewResponseDto;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository ReviewRepository;
    private final ReviewMapper ReviewMapper;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto request) {
        Review newReview = ReviewMapper.toEntity(request);
        Review savedReview = ReviewRepository.save(newReview);
        return ReviewMapper.toResponseDto(savedReview);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReview() {
        List<Review> Reviews = ReviewRepository.findAll();
        return Reviews.stream()
                .map(ReviewMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewById(Long id) {
        Review Review = findEntityById(id);
        return ReviewMapper.toResponseDto(Review);
    }

    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto request) {
        Review existingReview = findEntityById(id);
        ReviewMapper.update(request, existingReview);
        Review updatedReview = ReviewRepository.save(existingReview);
        return ReviewMapper.toResponseDto(updatedReview);
    }

    @Transactional
    public void deleteReview(Long id) {
        Review ReviewToDelete = findEntityById(id);
        ReviewRepository.delete(ReviewToDelete);
    }

    public Review findEntityById(Long id) {
        return ReviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review not found with id: " + id));
    }
}
