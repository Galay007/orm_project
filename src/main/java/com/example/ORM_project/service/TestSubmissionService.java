package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.TestSubmission;
import com.example.ORM_project.database.repository.TestSubmissionRepository;
import com.example.ORM_project.dto.TestSubmissionRequestDto;
import com.example.ORM_project.dto.TestSubmissionResponseDto;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.TestSubmissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestSubmissionService {
    private final TestSubmissionRepository testSubmissionRepository;
    private final TestSubmissionMapper testSubmissionMapper;

    @Transactional
    public TestSubmissionResponseDto createTest(TestSubmissionRequestDto request) {
        TestSubmission newTestSubmission = testSubmissionMapper.toEntity(request);
        TestSubmission savedTestSubmissionService = testSubmissionRepository.save(newTestSubmission);
        return testSubmissionMapper.toResponseDto(savedTestSubmissionService);
    }

    @Transactional(readOnly = true)
    public List<TestSubmissionResponseDto> getAllTest() {
        List<TestSubmission> testSubmissionServices = testSubmissionRepository.findAll();
        return testSubmissionServices.stream()
                .map(testSubmissionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TestSubmissionResponseDto getTestById(Long id) {
        TestSubmission testSubmission = findEntityById(id);
        return testSubmissionMapper.toResponseDto(testSubmission);
    }

    @Transactional
    public TestSubmissionResponseDto updateTest(Long id, TestSubmissionRequestDto request) {
        TestSubmission existingTestSubmission = findEntityById(id);
        testSubmissionMapper.update(request, existingTestSubmission);
        TestSubmission updatedTestSubmissionService = testSubmissionRepository.save(existingTestSubmission);
        return testSubmissionMapper.toResponseDto(updatedTestSubmissionService);
    }

    @Transactional
    public void deleteTest(Long id) {
        TestSubmission testSubmissionServiceToDelete = findEntityById(id);
        testSubmissionRepository.delete(testSubmissionServiceToDelete);
    }

    public TestSubmission findEntityById(Long id) {
        return testSubmissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Test not found with id: " + id));
    }
}
