package com.example.ORM_project;

import com.example.ORM_project.database.entity.Submission;
import com.example.ORM_project.database.repository.AssignmentRepository;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.SubmissionRequestDto;
import com.example.ORM_project.dto.SubmissionResponseDto;
import com.example.ORM_project.enums.Role;
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
public class SubmissionTest {
    @Autowired
    private com.example.ORM_project.controller.SubmissionController SubmissionController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestDataFactory testDataFactory;

    ResponseEntity<SubmissionResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createAssignment();
        testDataFactory.createStudent();

        Long firstAssignmentId = assignmentRepository.findAll().get(0).getId();
        Long firstStudentId  = userRepository.findByRole(Role.STUDENT).get(0).getId();

        SubmissionRequestDto submissionRequest = new SubmissionRequestDto();
        submissionRequest.setAssignment_id(firstAssignmentId);
        submissionRequest.setStudent_id(firstStudentId);
        submissionRequest.setSubmitted_at("31.12.2025");
        submissionRequest.setContent("Submission content...");

        responseCreated = SubmissionController.create(submissionRequest);
        System.out.println("BeforeEach создан Submission id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testSubmissionCreatePositive() {
        Submission submission = new Submission();
        SubmissionResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testSubmissionDeletePositive() {
        ResponseEntity<String> responseEntity = SubmissionController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testSubmissionUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        SubmissionRequestDto userForReplace = new SubmissionRequestDto();
        userForReplace.setContent("Submission content replace...");

        ResponseEntity<SubmissionResponseDto> responseEntity = SubmissionController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testSubmissionGetByIdPositive() {
        ResponseEntity<SubmissionResponseDto> responseEntity = SubmissionController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
