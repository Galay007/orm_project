package com.example.ORM_project;

import com.example.ORM_project.controller.QuestionController;
import com.example.ORM_project.database.entity.Question;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.TestCaseRepository;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.QuestionRequestDto;
import com.example.ORM_project.dto.QuestionResponseDto;
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
public class QuestionTest {
    @Autowired
    private QuestionController QuestionController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    private TestDataFactory testDataFactory;

    ResponseEntity<QuestionResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createTestCase();

        Long firstTestCaseId = testCaseRepository.findAll().get(0).getId();

        QuestionRequestDto questionRequest = new QuestionRequestDto();
        questionRequest.setTest_id(firstTestCaseId);
        questionRequest.setText("Question text...");

        responseCreated = QuestionController.create(questionRequest);
        System.out.println("BeforeEach создан Question id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testQuestionCreatePositive() {
        Question question = new Question();
        QuestionResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testTestDeletePositive() {
        ResponseEntity<String> responseEntity = QuestionController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testQuestionUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        QuestionRequestDto userForReplace = new QuestionRequestDto();
        userForReplace.setText("Question text replace...");

        ResponseEntity<QuestionResponseDto> responseEntity = QuestionController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testQuestionGetByIdPositive() {
        ResponseEntity<QuestionResponseDto> responseEntity = QuestionController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
