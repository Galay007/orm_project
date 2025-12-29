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
        TagResponseDto createdCategory = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAll() {
        List<TagResponseDto> categories = tagService.getAllTags();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getById(@PathVariable Long id) {
        TagResponseDto category = tagService.getTagById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> update(@PathVariable Long id, @RequestBody TagRequestDto request) {
        TagResponseDto updatedCategory = tagService.updateTag(id, request);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>("Tag " + id + " успешно удален", HttpStatus.OK);
    }
}
