package com.example.ORM_project;

import com.example.ORM_project.controller.CourseController;
import com.example.ORM_project.database.entity.Category;
import com.example.ORM_project.database.entity.Course;
import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.database.entity.Tag;
import com.example.ORM_project.database.repository.CategoryRepository;
import com.example.ORM_project.database.repository.TagRepository;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.CourseRequestDto;
import com.example.ORM_project.dto.CourseResponseDto;
import com.example.ORM_project.enums.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class CourseTest {
    @Autowired
    private CourseController courseController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;

    ResponseEntity<CourseResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        User newUser = new User();
        newUser.setFirstName("TEST");
        newUser.setLastName("TESTOVKSKIY");
        newUser.setEmail("test@example.com");
        newUser.setRole(Role.TEACHER);
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

        CourseRequestDto courseRequest = new CourseRequestDto();
        courseRequest.setTitle("Test Title");
        courseRequest.setDescription("Test description");
        courseRequest.setDuration("1 year");
        courseRequest.setStart_date("01.09.2025");
        courseRequest.setTeacher_id(savedUser.getId());
        courseRequest.setCategory_id(savedCategory.getId());
        courseRequest.setTag_ids(Set.of(savedTag1.getId(), savedTag2.getId()));

        responseCreated = courseController.create(courseRequest);
        System.out.println("BeforeEach создан course id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testCourseCreatePositive() {
        Course course = new Course();
        CourseResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testCourseDeletePositive() {
        ResponseEntity<String> responseEntity = courseController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testCourseUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        CourseRequestDto userForReplace = new CourseRequestDto();
        userForReplace.setTitle("Test Replace");
        userForReplace.setDuration("2 years");

        ResponseEntity<CourseResponseDto> responseEntity = courseController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testCourseGetByIdPositive() {
        ResponseEntity<CourseResponseDto> responseEntity = courseController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }

}
