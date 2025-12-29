package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Course;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.dto.CourseRequestDto;
import com.example.ORM_project.dto.CourseResponseDto;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto request) {

        Course newCourse = courseMapper.toEntity(request);
        Course savedCourse = courseRepository.save(newCourse);
        return courseMapper.toResponseDto(savedCourse);
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAllCourses() {
        List<Course> profiles = courseRepository.findAll();
        return profiles.stream()
                .map(courseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseResponseDto getCourseById(Long id) {
        Course Course = findEntityById(id);
        return courseMapper.toResponseDto(Course);
    }

    @Transactional
    public CourseResponseDto updateCourse(Long id, CourseRequestDto request) {
        Course existingCategory = findEntityById(id);
        courseMapper.update(request, existingCategory);
        Course updatedCategory = courseRepository.save(existingCategory);
        return courseMapper.toResponseDto(updatedCategory);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course categoryToDelete = findEntityById(id);
        courseRepository.delete(categoryToDelete);
    }

    public Course findEntityById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + id));
    }
}
