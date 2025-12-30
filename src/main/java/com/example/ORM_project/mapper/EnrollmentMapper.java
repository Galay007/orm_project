package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Enrollment;
import com.example.ORM_project.database.entity.Module;
import com.example.ORM_project.dto.EnrollmentRequestDto;
import com.example.ORM_project.dto.EnrollmentResponseDto;
import com.example.ORM_project.dto.ModuleRequestDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EnrollmentMapper {
    @Mapping(target = "course.id", source = "course_id")
    @Mapping(target = "student.id", source = "student_id")
    Enrollment toEntity(EnrollmentRequestDto dto);

    @Mapping(source = "course.id", target = "course_id")
    @Mapping(source = "student.id", target = "student_id")
    EnrollmentResponseDto toResponseDto(Enrollment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(EnrollmentRequestDto dto, @MappingTarget Enrollment entity);
}

