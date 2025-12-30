package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.AnswerOption;
import com.example.ORM_project.dto.AnswerOptionRequestDto;
import com.example.ORM_project.dto.AnswerOptionResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AnswerOptionMapper {
    @Mapping(target = "question.id", source = "question_id")
    AnswerOption toEntity(AnswerOptionRequestDto dto);

    @Mapping(source = "question.id", target = "question_id")
    AnswerOptionResponseDto toResponseDto(AnswerOption entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(AnswerOptionRequestDto dto, @MappingTarget AnswerOption entity);
}
