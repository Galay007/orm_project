package com.example.ORM_project.controller;

import com.example.ORM_project.dto.ModuleRequestDto;
import com.example.ORM_project.dto.ModuleResponseDto;
import com.example.ORM_project.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/module")
public class ModuleController {
    private final ModuleService moduleService;

    @PostMapping
    public ResponseEntity<ModuleResponseDto> create(@RequestBody ModuleRequestDto request) {
        ModuleResponseDto createdModule = moduleService.createModule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModule);
    }

    @GetMapping
    public ResponseEntity<List<ModuleResponseDto>> getAll() {
        List<ModuleResponseDto> modules = moduleService.getAllModule();
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponseDto> getById(@PathVariable Long id) {
        ModuleResponseDto module = moduleService.getModuleById(id);
        return ResponseEntity.ok(module);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleResponseDto> update(@PathVariable Long id, @RequestBody ModuleRequestDto request) {
        ModuleResponseDto updatedModule = moduleService.updateModule(id, request);
        return ResponseEntity.ok(updatedModule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return new ResponseEntity<>("Module " + id + " успешно удален", HttpStatus.OK);
    }
}
