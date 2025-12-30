package com.example.ORM_project.controller;

import com.example.ORM_project.dto.TagRequestDto;
import com.example.ORM_project.dto.TagResponseDto;
import com.example.ORM_project.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tag")
public class TagController {


    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponseDto> create(@RequestBody TagRequestDto request) {
        TagResponseDto createdTag = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
    }

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAll() {
        List<TagResponseDto> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getById(@PathVariable Long id) {
        TagResponseDto tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> update(@PathVariable Long id, @RequestBody TagRequestDto request) {
        TagResponseDto updateTag = tagService.updateTag(id, request);
        return ResponseEntity.ok(updateTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>("Tag " + id + " успешно удален", HttpStatus.OK);
    }
}
