package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Submission;
import com.example.ORM_project.database.repository.SubmissionRepository;
import com.example.ORM_project.mapper.SubmissionMapper;
import com.example.ORM_project.dto.SubmissionRequestDto;
import com.example.ORM_project.dto.SubmissionResponseDto;
import com.example.ORM_project.exeptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubmissionService {
    private final SubmissionRepository SubmissionRepository;
    private final SubmissionMapper SubmissionMapper;

    @Transactional
    public SubmissionResponseDto createSubmission(SubmissionRequestDto request) {
        Submission newSubmission = SubmissionMapper.toEntity(request);
        Submission savedSubmission = SubmissionRepository.save(newSubmission);
        return SubmissionMapper.toResponseDto(savedSubmission);
    }

    @Transactional(readOnly = true)
    public List<SubmissionResponseDto> getAllSubmission() {
        List<com.example.ORM_project.database.entity.Submission> Submissions = SubmissionRepository.findAll();
        return Submissions.stream()
                .map(SubmissionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubmissionResponseDto getSubmissionById(Long id) {
        Submission Submission = findEntityById(id);
        return SubmissionMapper.toResponseDto(Submission);
    }

    @Transactional
    public SubmissionResponseDto updateSubmission(Long id, SubmissionRequestDto request) {
        Submission existingSubmission = findEntityById(id);
        SubmissionMapper.update(request, existingSubmission);
        Submission updatedSubmission = SubmissionRepository.save(existingSubmission);
        return SubmissionMapper.toResponseDto(updatedSubmission);
    }

    @Transactional
    public void deleteSubmission(Long id) {
        Submission SubmissionToDelete = findEntityById(id);
        SubmissionRepository.delete(SubmissionToDelete);
    }

    public Submission findEntityById(Long id) {
        return SubmissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Submission not found with id: " + id));
    }
}
