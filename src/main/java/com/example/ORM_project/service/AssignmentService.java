package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Assignment;
import com.example.ORM_project.database.repository.AssignmentRepository;
import com.example.ORM_project.dto.AssignmentRequestDto;
import com.example.ORM_project.dto.AssignmentResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.AssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AssignmentService {
    private final AssignmentRepository AssignmentRepository;
    private final AssignmentMapper AssignmentMapper;

    @Transactional
    public AssignmentResponseDto createAssignment(AssignmentRequestDto request) {
        if (AssignmentRepository.existsByDescription(request.getDescription())) {
            throw new DuplicateException("Assignment name " + request.getDescription() + " already exists.");
        }
        Assignment newAssignment = AssignmentMapper.toEntity(request);
        Assignment savedAssignment = AssignmentRepository.save(newAssignment);
        return AssignmentMapper.toResponseDto(savedAssignment);
    }

    @Transactional(readOnly = true)
    public List<AssignmentResponseDto> getAllAssignment() {
        List<Assignment> categories = AssignmentRepository.findAll();
        return categories.stream()
                .map(AssignmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AssignmentResponseDto getAssignmentById(Long id) {
        Assignment Assignment = findEntityById(id);
        return AssignmentMapper.toResponseDto(Assignment);
    }

    @Transactional
    public AssignmentResponseDto updateAssignment(Long id, AssignmentRequestDto request) {
        Assignment existingAssignment = findEntityById(id);
        AssignmentMapper.update(request, existingAssignment);
        Assignment updatedAssignment = AssignmentRepository.save(existingAssignment);
        return AssignmentMapper.toResponseDto(updatedAssignment);
    }

    @Transactional
    public void deleteAssignment(Long id) {
        Assignment AssignmentToDelete = findEntityById(id);
        AssignmentRepository.delete(AssignmentToDelete);
    }

    public Assignment findEntityById(Long id) {
        return AssignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assignment not found with id: " + id));
    }
}
