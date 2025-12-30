package com.example.ORM_project;

import com.example.ORM_project.controller.ModuleController;
import com.example.ORM_project.database.entity.Module;
import com.example.ORM_project.database.repository.CourseRepository;
import com.example.ORM_project.dto.*;
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
public class ModuleRestApiTest {
    @Autowired
    private ModuleController moduleController;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestDataFactory testDataFactory;

    ResponseEntity<ModuleResponseDto> responseCreated = null;

    @BeforeEach
    void sendRequest() {
        testDataFactory.createTestCourse(Role.TEACHER);

        Long firstCourseId = courseRepository.findAll().get(0).getId();

        ModuleRequestDto moduleRequest = new ModuleRequestDto();
        moduleRequest.setCourse_id(firstCourseId);
        moduleRequest.setTitle("Test module title");

        responseCreated = moduleController.create(moduleRequest);
        System.out.println("BeforeEach создан module id: " + responseCreated.getBody().getId());
    }

    @Test
    @Order(1)
    void testModuleCreatePositive() {
        Module module = new Module();
        ModuleResponseDto userResponse = responseCreated.getBody();
        HttpStatusCode responseCode = responseCreated.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    void testModuleDeletePositive() {
        ResponseEntity<String> responseEntity = moduleController.delete(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();
        responseCreated = null;

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(3)
    void testModuleUpdatePositive() {
        Long userIdToUpdate = responseCreated.getBody().getId();

        ModuleRequestDto userForReplace = new ModuleRequestDto();
        userForReplace.setTitle("Test Replace");

        ResponseEntity<ModuleResponseDto> responseEntity = moduleController.update(userIdToUpdate, userForReplace);
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
    }

    @Test
    @Order(4)
    void testModuleGetByIdPositive() {
        ResponseEntity<ModuleResponseDto> responseEntity = moduleController.getById(responseCreated.getBody().getId());
        HttpStatusCode responseCode = responseEntity.getStatusCode();

        assertThat(responseCode.equals(HttpStatus.OK));
        assertThat(responseCreated.getBody().getId().equals(responseEntity.getBody().getId()));
    }

}
