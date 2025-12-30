package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.TestCase;
import com.example.ORM_project.dto.TestCaseRequestDto;
import com.example.ORM_project.dto.TestCaseResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ORM_project.database.repository.TestCaseRepository;
import com.example.ORM_project.mapper.TestCaseMapper;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final TestCaseMapper testCaseMapper;

    @Transactional
    public TestCaseResponseDto createTest(TestCaseRequestDto request) {
        if (testCaseRepository.existsByTitle(request.getTitle())) {
            throw new DuplicateException("Test name " + request.getTitle() + " already exists.");
        }

        TestCase newTestCase = testCaseMapper.toEntity(request);
        TestCase savedTestCase = testCaseRepository.save(newTestCase);
        return testCaseMapper.toResponseDto(savedTestCase);
    }

    @Transactional(readOnly = true)
    public List<TestCaseResponseDto> getAllTest() {
        List<TestCase> testCases = testCaseRepository.findAll();
        return testCases.stream()
                .map(testCaseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TestCaseResponseDto getTestById(Long id) {
        TestCase TestCase = findEntityById(id);
        return testCaseMapper.toResponseDto(TestCase);
    }

    @Transactional
    public TestCaseResponseDto updateTest(Long id, TestCaseRequestDto request) {
        TestCase existingTestCase = findEntityById(id);
        testCaseMapper.update(request, existingTestCase);
        TestCase updatedTestCase = testCaseRepository.save(existingTestCase);
        return testCaseMapper.toResponseDto(updatedTestCase);
    }

    @Transactional
    public void deleteTest(Long id) {
        TestCase testCaseToDelete = findEntityById(id);
        testCaseRepository.delete(testCaseToDelete);
    }

    public TestCase findEntityById(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TestCase not found with id: " + id));
    }
}
