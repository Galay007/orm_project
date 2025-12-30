package com.example.ORM_project;

import com.example.ORM_project.controller.TestCaseController;
import com.example.ORM_project.database.entity.TestCase;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.ModuleRepository;
import com.example.ORM_project.dto.TestCaseRequestDto;
import com.example.ORM_project.dto.TestCaseResponseDto;
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
public class TestCaseTest {
    @Autowired
    private TestCaseController TestCaseController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private TestDataFactory testDataFactory;

    ResponseEntity<TestCaseResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createModule();

        Long firstModuleId = moduleRepository.findAll().get(0).getId();

        TestCaseRequestDto testCaseRequest = new TestCaseRequestDto();
        testCaseRequest.setModule_id(firstModuleId);
        testCaseRequest.setTitle("Test TestCase title");

        responseCreated = TestCaseController.create(testCaseRequest);
        System.out.println("BeforeEach создан TestCase id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testTestCaseCreatePositive() {
        TestCase testCase = new TestCase();
        TestCaseResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testTestDeletePositive() {
        ResponseEntity<String> responseEntity = TestCaseController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testTestCaseUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        TestCaseRequestDto userForReplace = new TestCaseRequestDto();
        userForReplace.setTitle("Test Replace");

        ResponseEntity<TestCaseResponseDto> responseEntity = TestCaseController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testTestCaseGetByIdPositive() {
        ResponseEntity<TestCaseResponseDto> responseEntity = TestCaseController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
