package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Lesson;
import com.example.ORM_project.dto.LessonRequestDto;
import com.example.ORM_project.dto.LessonResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LessonMapper {
    @Mapping(target = "module.id", source = "module_id")
    com.example.ORM_project.database.entity.Lesson toEntity(LessonRequestDto dto);

    @Mapping(source = "module.id", target = "module_id")
    LessonResponseDto toResponseDto(com.example.ORM_project.database.entity.Lesson entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(LessonRequestDto dto, @MappingTarget Lesson entity);
}
