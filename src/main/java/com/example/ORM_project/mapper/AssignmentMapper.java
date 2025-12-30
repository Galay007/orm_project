package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Assignment;
import com.example.ORM_project.dto.AssignmentRequestDto;
import com.example.ORM_project.dto.AssignmentResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AssignmentMapper {
    @Mapping(target = "lesson.id", source = "lesson_id")
    Assignment toEntity(AssignmentRequestDto dto);

    @Mapping(source = "lesson.id", target = "lesson_id")
    AssignmentResponseDto toResponseDto(Assignment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(AssignmentRequestDto dto, @MappingTarget Assignment entity);
}
