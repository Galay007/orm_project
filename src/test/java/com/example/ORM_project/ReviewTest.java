package com.example.ORM_project;

import com.example.ORM_project.controller.ReviewController;
import com.example.ORM_project.database.entity.Review;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.ReviewRequestDto;
import com.example.ORM_project.dto.ReviewResponseDto;
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
public class ReviewTest {
    @Autowired
    private ReviewController ReviewController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestDataFactory testDataFactory;
    @Autowired
    private UserRepository userRepository;

    ResponseEntity<ReviewResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createTestCourse(Role.TEACHER);
        testDataFactory.createStudent();

        Long firstCourseId = courseRepository.findAll().get(0).getId();
        Long firstStudentId  = userRepository.findByRole(Role.STUDENT).get(0).getId();

        ReviewRequestDto reviewRequest = new ReviewRequestDto();
        reviewRequest.setCourse_id(firstCourseId);
        reviewRequest.setStudent_id(firstStudentId);
        reviewRequest.setCreated_at("31.12.2025");
        reviewRequest.setRating(5);

        responseCreated = ReviewController.create(reviewRequest);
        System.out.println("BeforeEach создан Review id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testReviewCreatePositive() {
        Review Review = new Review();
        ReviewResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testReviewDeletePositive() {
        ResponseEntity<String> responseEntity = ReviewController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testReviewUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        ReviewRequestDto userForReplace = new ReviewRequestDto();
        userForReplace.setRating(10);

        ResponseEntity<ReviewResponseDto> responseEntity = ReviewController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testReviewGetByIdPositive() {
        ResponseEntity<ReviewResponseDto> responseEntity = ReviewController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
