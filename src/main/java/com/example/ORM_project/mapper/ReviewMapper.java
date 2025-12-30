package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Review;
import com.example.ORM_project.dto.ReviewRequestDto;
import com.example.ORM_project.dto.ReviewResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    @Mapping(target = "course.id", source = "course_id")
    @Mapping(target = "student.id", source = "student_id")
    @Mapping(target = "created_at", source = "created_at")
    Review toEntity(ReviewRequestDto dto);

    @Mapping(source = "course.id", target = "course_id")
    @Mapping(source = "student.id", target = "student_id")
    @Mapping(source = "created_at", target = "created_at")
    ReviewResponseDto toResponseDto(Review entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(ReviewRequestDto dto, @MappingTarget Review entity);
}
