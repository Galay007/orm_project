package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Enrollment;
import com.example.ORM_project.database.repository.EnrollmentRepository;
import com.example.ORM_project.dto.EnrollmentRequestDto;
import com.example.ORM_project.dto.EnrollmentResponseDto;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.EnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    public EnrollmentResponseDto createEnrollment(EnrollmentRequestDto request) {
        Enrollment newEnrollment = enrollmentMapper.toEntity(request);
        Enrollment savedEnrollment = enrollmentRepository.save(newEnrollment);
        return enrollmentMapper.toResponseDto(savedEnrollment);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponseDto> getAllEnrollment() {
        List<Enrollment> Enrollments = enrollmentRepository.findAll();
        return Enrollments.stream()
                .map(enrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EnrollmentResponseDto getEnrollmentById(Long id) {
        Enrollment Enrollment = findEntityById(id);
        return enrollmentMapper.toResponseDto(Enrollment);
    }

    @Transactional
    public EnrollmentResponseDto updateEnrollment(Long id, EnrollmentRequestDto request) {
        Enrollment existingEnrollment = findEntityById(id);
        enrollmentMapper.update(request, existingEnrollment);
        Enrollment updatedEnrollment = enrollmentRepository.save(existingEnrollment);
        return enrollmentMapper.toResponseDto(updatedEnrollment);
    }

    @Transactional
    public void deleteEnrollment(Long id) {
        Enrollment EnrollmentToDelete = findEntityById(id);
        enrollmentRepository.delete(EnrollmentToDelete);
    }

    public Enrollment findEntityById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enrollment not found with id: " + id));
    }
}
