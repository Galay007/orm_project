package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Category;
import com.example.ORM_project.dto.CategoryRequestDto;
import com.example.ORM_project.dto.CategoryResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "name", source = "name")
    Category toEntity(CategoryRequestDto dto);

    @Mapping(target = "name", source = "name")
    CategoryResponseDto toResponseDto(Category entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(CategoryRequestDto dto, @MappingTarget Category entity);
}
