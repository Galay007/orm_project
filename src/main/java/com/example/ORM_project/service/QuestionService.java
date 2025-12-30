package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Question;
import com.example.ORM_project.database.repository.QuestionRepository;
import com.example.ORM_project.mapper.QuestionMapper;
import com.example.ORM_project.dto.QuestionRequestDto;
import com.example.ORM_project.dto.QuestionResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository QuestionRepository;
    private final QuestionMapper questionMapper;

    @Transactional
    public QuestionResponseDto createQuestion(QuestionRequestDto request) {
        if (QuestionRepository.existsByText(request.getText())) {
            throw new DuplicateException("Question name " + request.getText() + " already exists.");
        }

        Question newQuestion = questionMapper.toEntity(request);
        Question savedQuestion = QuestionRepository.save(newQuestion);
        return questionMapper.toResponseDto(savedQuestion);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDto> getAllQuestion() {
        List<Question> Questions = QuestionRepository.findAll();
        return Questions.stream()
                .map(questionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestionById(Long id) {
        Question Question = findEntityById(id);
        return questionMapper.toResponseDto(Question);
    }

    @Transactional
    public QuestionResponseDto updateQuestion(Long id, QuestionRequestDto request) {
        Question existingQuestion = findEntityById(id);
        questionMapper.update(request, existingQuestion);
        Question updatedQuestion = QuestionRepository.save(existingQuestion);
        return questionMapper.toResponseDto(updatedQuestion);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        Question QuestionToDelete = findEntityById(id);
        QuestionRepository.delete(QuestionToDelete);
    }

    public Question findEntityById(Long id) {
        return QuestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found with id: " + id));
    }
}
