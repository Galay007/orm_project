package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.TestSubmission;
import com.example.ORM_project.dto.TestSubmissionRequestDto;
import com.example.ORM_project.dto.TestSubmissionResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TestSubmissionMapper {
    @Mapping(target = "student.id", source = "student_id")
    @Mapping(target = "test.id", source = "test_id")
    TestSubmission toEntity(TestSubmissionRequestDto dto);

    @Mapping(source = "student.id", target = "student_id")
    @Mapping(source = "test.id", target = "test_id")
    TestSubmissionResponseDto toResponseDto(TestSubmission entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(TestSubmissionRequestDto dto, @MappingTarget TestSubmission entity);
}
