package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.dto.UserRequestDto;
import com.example.ORM_project.dto.UserResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    User toEntity(UserRequestDto dto);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    UserResponseDto toResponseDto(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UserRequestDto dto, @MappingTarget User entity);
}
