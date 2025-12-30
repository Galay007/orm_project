package com.example.ORM_project;

import com.example.ORM_project.controller.LessonController;
import com.example.ORM_project.database.entity.Lesson;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.ModuleRepository;
import com.example.ORM_project.dto.LessonRequestDto;
import com.example.ORM_project.dto.LessonResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class LessonTest {
    @Autowired
    private LessonController lessonController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private TestDataFactory testDataFactory;

    ResponseEntity<LessonResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createModule();

        Long firstModuleId = moduleRepository.findAll().get(0).getId();

        LessonRequestDto lessonRequest = new LessonRequestDto();
        lessonRequest.setModule_id(firstModuleId);
        lessonRequest.setTitle("Test Lesson title");
        lessonRequest.setContent("Test content");

        responseCreated = lessonController.create(lessonRequest);
        System.out.println("BeforeEach создан Lesson id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testLessonCreatePositive() {
        com.example.ORM_project.database.entity.Lesson Lesson = new Lesson();
        LessonResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testLessonDeletePositive() {
        ResponseEntity<String> responseEntity = lessonController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testLessonUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        LessonRequestDto userForReplace = new LessonRequestDto();
        userForReplace.setContent("Test Replace");

        ResponseEntity<LessonResponseDto> responseEntity = lessonController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testLessonGetByIdPositive() {
        ResponseEntity<LessonResponseDto> responseEntity = lessonController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
