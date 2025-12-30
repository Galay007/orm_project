package com.example.ORM_project;

import com.example.ORM_project.controller.UserController;
import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.dto.UserRequestDto;
import com.example.ORM_project.dto.UserResponseDto;
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
public class UserTest {
    @Autowired
    private UserController userController;

    ResponseEntity<UserResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setFirstName("TEST");
        userRequest.setLastName("TESTOVKSKIY");
        userRequest.setEmail("test@example.com");
        userRequest.setRole(Role.TEACHER);

        responseCreated = userController.create(userRequest);
        System.out.println("BeforeEach создан пользователь id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testUserCreatePositive() {
        User user = new User();
        UserResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testUserDeletePositive() {
        ResponseEntity<String> responseEntity = userController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testUserUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        UserRequestDto userForReplace = new UserRequestDto();
        userForReplace.setFirstName("REPLACE");
        userForReplace.setLastName("REPLACE");
        userForReplace.setEmail("test@example.com");
        userForReplace.setRole(Role.STUDENT);

        ResponseEntity<UserResponseDto> responseEntity = userController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testUserGetByIdPositive() {
        ResponseEntity<UserResponseDto> responseEntity = userController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }
}
