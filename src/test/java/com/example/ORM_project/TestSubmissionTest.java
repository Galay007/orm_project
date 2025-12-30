package com.example.ORM_project;

import com.example.ORM_project.controller.TestSubmissionController;
import com.example.ORM_project.database.entity.TestSubmission;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.TestCaseRepository;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.TestSubmissionRequestDto;
import com.example.ORM_project.dto.TestSubmissionResponseDto;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class TestSubmissionTest {
    @Autowired
    private TestSubmissionController TestSubmissionController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    private TestDataFactory testDataFactory;

    ResponseEntity<TestSubmissionResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createTestCase();
        testDataFactory.createStudent();

        Long firstCaseTest = testCaseRepository.findAll().get(0).getId();
        Long firstStudentId  = userRepository.findByRole(Role.STUDENT).get(0).getId();


        TestSubmissionRequestDto testSubmissionRequest = new TestSubmissionRequestDto();
        testSubmissionRequest.setTest_id(firstCaseTest);
        testSubmissionRequest.setStudent_id(firstStudentId);
        testSubmissionRequest.setScore(50);
        testSubmissionRequest.setTaken_at("31.12.2025");

        responseCreated = TestSubmissionController.create(testSubmissionRequest);
        System.out.println("BeforeEach создан TestSubmission id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testTestSubmissionCreatePositive() {
        TestSubmission testSubmission = new TestSubmission();
        TestSubmissionResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testTestDeletePositive() {
        ResponseEntity<String> responseEntity = TestSubmissionController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testTestSubmissionUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        TestSubmissionRequestDto userForReplace = new TestSubmissionRequestDto();
        userForReplace.setScore(100);

        ResponseEntity<TestSubmissionResponseDto> responseEntity = TestSubmissionController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testTestSubmissionGetByIdPositive() {
        ResponseEntity<TestSubmissionResponseDto> responseEntity = TestSubmissionController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
