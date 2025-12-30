package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.TestCase;
import com.example.ORM_project.dto.TestCaseRequestDto;
import com.example.ORM_project.dto.TestCaseResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TestCaseMapper {
    @Mapping(target = "module.id", source = "module_id")
    TestCase toEntity(TestCaseRequestDto dto);

    @Mapping(source = "module.id", target = "module_id")
    TestCaseResponseDto toResponseDto(TestCase entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(TestCaseRequestDto dto, @MappingTarget TestCase entity);
}
