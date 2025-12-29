package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Tag;
import com.example.ORM_project.database.repository.TagRepository;
import com.example.ORM_project.dto.TagRequestDto;
import com.example.ORM_project.dto.TagResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Transactional
    public TagResponseDto createTag(TagRequestDto request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new DuplicateException("Tag name " + request.getName() + " already exists.");
        }
        Tag newTag = tagMapper.toEntity(request);
        Tag savedCategory = tagRepository.save(newTag);
        return tagMapper.toResponseDto(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<TagResponseDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(tagMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TagResponseDto getTagById(Long id) {
        Tag Tag = findEntityById(id);
        return tagMapper.toResponseDto(Tag);
    }

    @Transactional
    public TagResponseDto updateTag(Long id, TagRequestDto request) {
        Tag existingCategory = findEntityById(id);
        tagMapper.update(request, existingCategory);
        Tag updatedCategory = tagRepository.save(existingCategory);
        return tagMapper.toResponseDto(updatedCategory);
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag categoryToDelete = findEntityById(id);
        tagRepository.delete(categoryToDelete);
    }

    public Tag findEntityById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found with id: " + id));
    }
}
