package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Module;
import com.example.ORM_project.dto.ModuleRequestDto;
import com.example.ORM_project.dto.ModuleResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {
    @Mapping(target = "course.id", source = "course_id")
    Module toEntity(ModuleRequestDto dto);

    @Mapping(source = "course.id", target = "course_id")
    ModuleResponseDto toResponseDto(Module entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(ModuleRequestDto dto, @MappingTarget Module entity);
}
