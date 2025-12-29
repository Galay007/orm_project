package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Category;
import com.example.ORM_project.database.repository.CategoryRepository;
import com.example.ORM_project.dto.CategoryRequestDto;
import com.example.ORM_project.dto.CategoryResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateException("Category name " + request.getName() + " already exists.");
        }
        Category newCategory = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(newCategory);
        return categoryMapper.toResponseDto(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = findEntityById(id);
        return categoryMapper.toResponseDto(category);
    }

    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto request) {
        Category existingCategory = findEntityById(id);
        categoryMapper.update(request, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toResponseDto(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category categoryToDelete = findEntityById(id);
        categoryRepository.delete(categoryToDelete);
    }

    public Category findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }
}
