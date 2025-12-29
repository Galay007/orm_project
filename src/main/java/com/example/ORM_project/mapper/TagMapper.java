package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Tag;
import com.example.ORM_project.dto.TagRequestDto;
import com.example.ORM_project.dto.TagResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "name", source = "name")
    Tag toEntity(TagRequestDto dto);

    @Mapping(target = "name", source = "name")
    TagResponseDto toResponseDto(Tag entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(TagRequestDto dto, @MappingTarget Tag entity);
}
