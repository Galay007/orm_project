package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Module;
import com.example.ORM_project.database.repository.ModuleRepository;
import com.example.ORM_project.dto.ModuleRequestDto;
import com.example.ORM_project.dto.ModuleResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.ModuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    @Transactional
    public ModuleResponseDto createModule(ModuleRequestDto request) {
        if (moduleRepository.existsByTitle(request.getTitle())) {
            throw new DuplicateException("Module name " + request.getTitle() + " already exists.");
        }

        Module newModule = moduleMapper.toEntity(request);
        Module savedModule = moduleRepository.save(newModule);
        return moduleMapper.toResponseDto(savedModule);
    }

    @Transactional(readOnly = true)
    public List<ModuleResponseDto> getAllModule() {
        List<Module> modules = moduleRepository.findAll();
        return modules.stream()
                .map(moduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ModuleResponseDto getModuleById(Long id) {
        Module Module = findEntityById(id);
        return moduleMapper.toResponseDto(Module);
    }

    @Transactional
    public ModuleResponseDto updateModule(Long id, ModuleRequestDto request) {
        Module existingModule = findEntityById(id);
        moduleMapper.update(request, existingModule);
        Module updatedModule = moduleRepository.save(existingModule);
        return moduleMapper.toResponseDto(updatedModule);
    }

    @Transactional
    public void deleteModule(Long id) {
        Module moduleToDelete = findEntityById(id);
        moduleRepository.delete(moduleToDelete);
    }

    public Module findEntityById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Module not found with id: " + id));
    }
}
