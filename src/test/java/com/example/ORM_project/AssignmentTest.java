package com.example.ORM_project;

import com.example.ORM_project.controller.AssignmentController;
import com.example.ORM_project.database.entity.Assignment;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.LessonRepository;
import com.example.ORM_project.dto.AssignmentRequestDto;
import com.example.ORM_project.dto.AssignmentResponseDto;
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
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssignmentTest {
    @Autowired
    private AssignmentController AssignmentController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TestDataFactory testDataFactory;

    ResponseEntity<AssignmentResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createLesson();

        Long firstLessonId = lessonRepository.findAll().get(0).getId();

        AssignmentRequestDto assignmentRequest = new AssignmentRequestDto();
        assignmentRequest.setLesson_id(firstLessonId);
        assignmentRequest.setDescription("Assignment description...");

        responseCreated = AssignmentController.create(assignmentRequest);
        System.out.println("BeforeEach создан Assignment id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testAssignmentCreatePositive() {
        Assignment assignment = new Assignment();
        AssignmentResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testAssignmentDeletePositive() {
        ResponseEntity<String> responseEntity = AssignmentController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testAssignmentUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        AssignmentRequestDto userForReplace = new AssignmentRequestDto();
        userForReplace.setDescription("Assignment description replace...");

        ResponseEntity<AssignmentResponseDto> responseEntity = AssignmentController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testAssignmentGetByIdPositive() {
        ResponseEntity<AssignmentResponseDto> responseEntity = AssignmentController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
