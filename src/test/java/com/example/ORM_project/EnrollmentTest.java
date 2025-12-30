package com.example.ORM_project;

import com.example.ORM_project.controller.EnrollmentController;
import com.example.ORM_project.database.entity.Enrollment;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.EnrollmentRequestDto;
import com.example.ORM_project.dto.EnrollmentResponseDto;
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
public class EnrollmentTest {
    @Autowired
    private EnrollmentController EnrollmentController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestDataFactory testDataFactory;
    @Autowired
    private UserRepository userRepository;

    ResponseEntity<EnrollmentResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createTestCourse(Role.TEACHER);
        testDataFactory.createStudent();

        Long firstCourseId = courseRepository.findAll().get(0).getId();
        Long firstStudentId  = userRepository.findByRole(Role.STUDENT).get(0).getId();

        EnrollmentRequestDto enrollmentRequest = new EnrollmentRequestDto();
        enrollmentRequest.setCourse_id(firstCourseId);
        enrollmentRequest.setStudent_id(firstStudentId);
        enrollmentRequest.setEnroll_date("01.09.2025");

        responseCreated = EnrollmentController.create(enrollmentRequest);
        System.out.println("BeforeEach создан Enrollment id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testEnrollmentCreatePositive() {
        Enrollment Enrollment = new Enrollment();
        EnrollmentResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testEnrollmentDeletePositive() {
        ResponseEntity<String> responseEntity = EnrollmentController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testEnrollmentUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        EnrollmentRequestDto userForReplace = new EnrollmentRequestDto();
        userForReplace.setEnroll_date("30.09.2025");

        ResponseEntity<EnrollmentResponseDto> responseEntity = EnrollmentController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testEnrollmentGetByIdPositive() {
        ResponseEntity<EnrollmentResponseDto> responseEntity = EnrollmentController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
