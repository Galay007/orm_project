package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.AnswerOption;
import com.example.ORM_project.database.repository.AnswerOptionRepository;
import com.example.ORM_project.mapper.AnswerOptionMapper;
import com.example.ORM_project.dto.AnswerOptionRequestDto;
import com.example.ORM_project.dto.AnswerOptionResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerOptionService {
    private final AnswerOptionRepository AnswerOptionRepository;
    private final AnswerOptionMapper AnswerOptionMapper;

    @Transactional
    public AnswerOptionResponseDto createAnswerOption(AnswerOptionRequestDto request) {
        if (AnswerOptionRepository.existsByText(request.getText())) {
            throw new DuplicateException("AnswerOption name " + request.getText() + " already exists.");
        }
        AnswerOption newAnswerOption = AnswerOptionMapper.toEntity(request);
        AnswerOption savedAnswerOption = AnswerOptionRepository.save(newAnswerOption);
        return AnswerOptionMapper.toResponseDto(savedAnswerOption);
    }

    @Transactional(readOnly = true)
    public List<AnswerOptionResponseDto> getAllAnswerOption() {
        List<AnswerOption> categories = AnswerOptionRepository.findAll();
        return categories.stream()
                .map(AnswerOptionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnswerOptionResponseDto getAnswerOptionById(Long id) {
        AnswerOption AnswerOption = findEntityById(id);
        return AnswerOptionMapper.toResponseDto(AnswerOption);
    }

    @Transactional
    public AnswerOptionResponseDto updateAnswerOption(Long id, AnswerOptionRequestDto request) {
        AnswerOption existingAnswerOption = findEntityById(id);
        AnswerOptionMapper.update(request, existingAnswerOption);
        AnswerOption updatedAnswerOption = AnswerOptionRepository.save(existingAnswerOption);
        return AnswerOptionMapper.toResponseDto(updatedAnswerOption);
    }

    @Transactional
    public void deleteAnswerOption(Long id) {
        AnswerOption AnswerOptionToDelete = findEntityById(id);
        AnswerOptionRepository.delete(AnswerOptionToDelete);
    }

    public AnswerOption findEntityById(Long id) {
        return AnswerOptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AnswerOption not found with id: " + id));
    }
}
