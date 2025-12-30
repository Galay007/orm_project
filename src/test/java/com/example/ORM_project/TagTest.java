package com.example.ORM_project;

import com.example.ORM_project.controller.TagController;
import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.dto.TagRequestDto;
import com.example.ORM_project.dto.TagResponseDto;
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
public class TagTest {
    @Autowired
    private TagController tagController;

    ResponseEntity<TagResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        TagRequestDto categoryRequest = new TagRequestDto();
        categoryRequest.setName("TEST");
        responseCreated = tagController.create(categoryRequest);
        System.out.println("BeforeEach создан tag id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testCategoryCreatePositive() {
        User user = new User();
        TagResponseDto categoryResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testCategoryDeletePositive() {
        ResponseEntity<String> responseEntity = tagController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testCategoryUpdatePositive() {
        Long categoryIdToReplace = responseCreated.getBody().getId();

        TagRequestDto userForReplace = new TagRequestDto();
        userForReplace.setName("REPLACE");

        ResponseEntity<TagResponseDto> responseEntity = tagController.update(categoryIdToReplace, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testCategoryGetByIdPositive() {
        ResponseEntity<TagResponseDto> responseEntity = tagController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }

    @AfterEach
    void deleteIfExists() {

        if (responseCreated != null) {
            tagController.delete(responseCreated.getBody().getId());
        }
    }
}
