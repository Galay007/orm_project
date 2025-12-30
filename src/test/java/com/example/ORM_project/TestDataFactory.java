package com.example.ORM_project;

import com.example.ORM_project.controller.CourseController;
import com.example.ORM_project.database.entity.Category;
import com.example.ORM_project.database.entity.Tag;
import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.database.repository.CategoryRepository;
import com.example.ORM_project.database.repository.TagRepository;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.CourseRequestDto;
import com.example.ORM_project.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TestDataFactory {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CourseController courseController;

    public void createTestCourse(Role role) {
        User newUser = new User();
        newUser.setFirstName("TEST");
        newUser.setLastName("TESTOVKSKIY");
        newUser.setEmail("test@example.com");
        newUser.setRole(role);
        User savedUser = userRepository.save(newUser);

        Category category = new Category();
        category.setName("Test_category");
        Category savedCategory = categoryRepository.save(category);

        Tag tag1 = new Tag();
        tag1.setName("Test_tag1");
        Tag savedTag1 = tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("Test_tag2");
        Tag savedTag2 = tagRepository.save(tag2);

        CourseRequestDto request = new CourseRequestDto();
        request.setTitle("Test Title");
        request.setDescription("Test description");
        request.setDuration("1 year");
        request.setStart_date("01.09.2025");
        request.setTeacher_id(savedUser.getId());
        request.setCategory_id(savedCategory.getId());
        request.setTag_ids(Set.of(savedTag1.getId(), savedTag2.getId()));

        courseController.create(request);
    }

    public void createStudent(){
        User newUser = new User();
        newUser.setFirstName("Student");
        newUser.setLastName("Student");
        newUser.setEmail("student@example.com");
        newUser.setRole(Role.STUDENT);
        userRepository.save(newUser);
    }
}
