package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Profile;
import com.example.ORM_project.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {
    @Mapping(target = "user.id", source = "user_id")
    Profile toEntity(ProfileRequestDto dto);

    @Mapping(source = "user.id", target = "user_id")
    ProfileResponseDto toResponseDto(Profile entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(ProfileRequestDto dto, @MappingTarget Profile entity);
}
