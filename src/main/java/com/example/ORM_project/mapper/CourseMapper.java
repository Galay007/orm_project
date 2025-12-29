package com.example.ORM_project.mapper;

import com.example.ORM_project.database.entity.Course;
import com.example.ORM_project.database.entity.Tag;
import com.example.ORM_project.dto.CourseRequestDto;
import com.example.ORM_project.dto.CourseResponseDto;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {
    @Mapping(target = "teacher.id", source = "teacher_id")
    @Mapping(target = "category.id", source = "category_id")
    @Mapping(target = "tags", source = "tag_ids")
    Course toEntity(CourseRequestDto dto);

    @Mapping(source = "teacher.id", target = "teacher_id")
    @Mapping(source = "category.id", target = "category_id")
    @Mapping(source = "tags", target = "tag_ids")
    CourseResponseDto toResponseDto(Course entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "teacher.id", source = "teacher_id")
    @Mapping(target = "category.id", source = "category_id")
    @Mapping(target = "tags", source = "tag_ids")
    void update(CourseRequestDto dto, @MappingTarget Course entity);

    default Tag longToTag(Long id) {
        if (id == null) return null;
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }

    // Tag → Long
    default Long tagToLong(Tag tag) {
        return tag != null ? tag.getId() : null;
    }

    // Set<Long> → Set<Tag>
    default Set<Tag> mapTagIdsToTags(Set<Long> tagIds) {
        if (tagIds == null) return null;
        return tagIds.stream()
                .map(this::longToTag)
                .collect(java.util.stream.Collectors.toSet());
    }

    // Set<Tag> → Set<Long>
    default Set<Long> mapTagsToTagIds(Set<Tag> tags) {
        if (tags == null) return null;
        return tags.stream()
                .map(this::tagToLong)
                .collect(java.util.stream.Collectors.toSet());
    }
}
