package com.example.ORM_project;

import com.example.ORM_project.controller.ProfileController;
import com.example.ORM_project.database.entity.Profile;
import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.ProfileRequestDto;
import com.example.ORM_project.dto.ProfileResponseDto;
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
public class ProfileRestApiTest {
    @Autowired
    private ProfileController profileController;
    @Autowired
    private UserRepository userRepository;

    ResponseEntity<ProfileResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        User newUser = new User();
        newUser.setFirstName("TEST");
        newUser.setLastName("TESTOVKSKIY");
        newUser.setEmail("test@example.com");
        newUser.setRole(Role.TEACHER);
        User savedUser = userRepository.save(newUser);

        ProfileRequestDto profileRequest = new ProfileRequestDto();
        profileRequest.setUser_id(savedUser.getId());
        profileRequest.setBio("Test biography");
        profileRequest.setAvatarUrl("http://test.png");

        responseCreated = profileController.create(profileRequest);
        System.out.println("BeforeEach создан profile id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testProfileCreatePositive() {
        Profile profile = new Profile();
        ProfileResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testProfileDeletePositive() {
        ResponseEntity<String> responseEntity = profileController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testProfileUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        ProfileRequestDto userForReplace = new ProfileRequestDto();
        userForReplace.setUser_id(1L);
        userForReplace.setBio("REPLACE");
        userForReplace.setAvatarUrl("http://test.png");

        ResponseEntity<ProfileResponseDto> responseEntity = profileController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testProfileGetByIdPositive() {
        ResponseEntity<ProfileResponseDto> responseEntity = profileController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }

    @AfterEach
    void deleteIfExists() {
        if (responseCreated != null) {
            profileController.delete(responseCreated.getBody().getId());
        }
    }
}
