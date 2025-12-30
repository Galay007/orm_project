package com.example.ORM_project;

import com.example.ORM_project.controller.CategoryController;
import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.dto.CategoryRequestDto;
import com.example.ORM_project.dto.CategoryResponseDto;
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
public class CategoryRestApiTest {
    @Autowired
    private CategoryController categoryController;

    ResponseEntity<CategoryResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        CategoryRequestDto categoryRequest = new CategoryRequestDto();
        categoryRequest.setName("TEST");
        responseCreated = categoryController.create(categoryRequest);
        System.out.println("BeforeEach создан category id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testCategoryCreatePositive() {
        User user = new User();
        CategoryResponseDto categoryResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testCategoryDeletePositive() {
        ResponseEntity<String> responseEntity = categoryController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testCategoryUpdatePositive() {
        Long categoryIdToReplace = responseCreated.getBody().getId();

        CategoryRequestDto userForReplace = new CategoryRequestDto();
        userForReplace.setName("REPLACE");

        ResponseEntity<CategoryResponseDto> responseEntity = categoryController.update(categoryIdToReplace, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testCategoryGetByIdPositive() {
        ResponseEntity<CategoryResponseDto> responseEntity = categoryController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
