package com.example.ORM_project.controller;

import com.example.ORM_project.dto.ProfileRequestDto;
import com.example.ORM_project.dto.ProfileResponseDto;
import com.example.ORM_project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    
    private final ProfileService profileService;
    
    @PostMapping
    public ResponseEntity<ProfileResponseDto> create(@RequestBody ProfileRequestDto request) {
        ProfileResponseDto createdProfile = profileService.createPofile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponseDto>> getAll() {
        List<ProfileResponseDto> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> getById(@PathVariable Long id) {
        ProfileResponseDto profile = profileService.getProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> update(@PathVariable Long id, @RequestBody ProfileRequestDto request) {
        ProfileResponseDto updatedProfile = profileService.updateProfile(id, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return new ResponseEntity<>("Profile " + id + " успешно удален", HttpStatus.OK);
    }
}
