package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Question;
import com.example.ORM_project.dto.QuestionRequestDto;
import com.example.ORM_project.dto.QuestionResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {
    @Mapping(target = "test.id", source = "test_id")
    Question toEntity(QuestionRequestDto dto);

    @Mapping(source = "test.id", target = "test_id")
    QuestionResponseDto toResponseDto(com.example.ORM_project.database.entity.Question entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(QuestionRequestDto dto, @MappingTarget Question entity);
}
