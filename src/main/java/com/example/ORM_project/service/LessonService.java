package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Lesson;
import com.example.ORM_project.database.repository.LessonRepository;
import com.example.ORM_project.mapper.LessonMapper;
import com.example.ORM_project.dto.LessonRequestDto;
import com.example.ORM_project.dto.LessonResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonService {
    private final LessonRepository LessonRepository;
    private final LessonMapper LessonMapper;

    @Transactional
    public LessonResponseDto createLesson(LessonRequestDto request) {
        if (LessonRepository.existsByTitle(request.getTitle())) {
            throw new DuplicateException("Lesson name " + request.getTitle() + " already exists.");
        }

        com.example.ORM_project.database.entity.Lesson newLesson = LessonMapper.toEntity(request);
        com.example.ORM_project.database.entity.Lesson savedLesson = LessonRepository.save(newLesson);
        return LessonMapper.toResponseDto(savedLesson);
    }

    @Transactional(readOnly = true)
    public List<LessonResponseDto> getAllLesson() {
        List<com.example.ORM_project.database.entity.Lesson> Lessons = LessonRepository.findAll();
        return Lessons.stream()
                .map(LessonMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LessonResponseDto getLessonById(Long id) {
        com.example.ORM_project.database.entity.Lesson Lesson = findEntityById(id);
        return LessonMapper.toResponseDto(Lesson);
    }

    @Transactional
    public LessonResponseDto updateLesson(Long id, LessonRequestDto request) {
        com.example.ORM_project.database.entity.Lesson existingLesson = findEntityById(id);
        LessonMapper.update(request, existingLesson);
        com.example.ORM_project.database.entity.Lesson updatedLesson = LessonRepository.save(existingLesson);
        return LessonMapper.toResponseDto(updatedLesson);
    }

    @Transactional
    public void deleteLesson(Long id) {
        com.example.ORM_project.database.entity.Lesson LessonToDelete = findEntityById(id);
        LessonRepository.delete(LessonToDelete);
    }

    public Lesson findEntityById(Long id) {
        return LessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found with id: " + id));
    }
}
