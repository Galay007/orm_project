package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Submission;
import com.example.ORM_project.dto.SubmissionRequestDto;
import com.example.ORM_project.dto.SubmissionResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SubmissionMapper {
    @Mapping(target = "assignment.id", source = "assignment_id")
    @Mapping(target = "student.id", source = "student_id")
    Submission toEntity(SubmissionRequestDto dto);

    @Mapping(source = "assignment.id", target = "assignment_id")
    @Mapping(source = "student.id", target = "student_id")
    SubmissionResponseDto toResponseDto(Submission entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(SubmissionRequestDto dto, @MappingTarget Submission entity);
}
