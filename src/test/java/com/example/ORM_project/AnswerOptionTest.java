package com.example.ORM_project;

import com.example.ORM_project.controller.AnswerOptionController;
import com.example.ORM_project.database.entity.AnswerOption;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.QuestionRepository;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.AnswerOptionRequestDto;
import com.example.ORM_project.dto.AnswerOptionResponseDto;
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
public class AnswerOptionTest {
    @Autowired
    private AnswerOptionController AnswerOptionController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestDataFactory testDataFactory;

    ResponseEntity<AnswerOptionResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createQuestion();

        Long firstQuestion = questionRepository.findAll().get(0).getId();

        AnswerOptionRequestDto answerOptionRequest = new AnswerOptionRequestDto();
        answerOptionRequest.setQuestion_id(firstQuestion);
        answerOptionRequest.setText("AnswerOption text...");

        responseCreated = AnswerOptionController.create(answerOptionRequest);
        System.out.println("BeforeEach создан AnswerOption id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testAnswerOptionCreatePositive() {
        AnswerOption answerOption = new AnswerOption();
        AnswerOptionResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testTestDeletePositive() {
        ResponseEntity<String> responseEntity = AnswerOptionController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testAnswerOptionUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        AnswerOptionRequestDto userForReplace = new AnswerOptionRequestDto();
        userForReplace.setText("AnswerOption text replace...");

        ResponseEntity<AnswerOptionResponseDto> responseEntity = AnswerOptionController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testAnswerOptionGetByIdPositive() {
        ResponseEntity<AnswerOptionResponseDto> responseEntity = AnswerOptionController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
