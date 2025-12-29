package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.Profile;
import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.database.repository.ProfileRepository;
import com.example.ORM_project.dto.ProfileRequestDto;
import com.example.ORM_project.dto.ProfileResponseDto;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Transactional
    public ProfileResponseDto createPofile(ProfileRequestDto request) {

        Profile newProfile = profileMapper.toEntity(request);
        Profile savedProfile = profileRepository.save(newProfile);
        return profileMapper.toResponseDto(savedProfile);
    }

    @Transactional(readOnly = true)
    public List<ProfileResponseDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(profileMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileById(Long id) {
        Profile profile = findEntityById(id);
        return profileMapper.toResponseDto(profile);
    }

    @Transactional
    public ProfileResponseDto updateProfile(Long id, ProfileRequestDto request) {
        Profile existingCategory = findEntityById(id);
        profileMapper.update(request, existingCategory);
        Profile updatedCategory = profileRepository.save(existingCategory);
        return profileMapper.toResponseDto(updatedCategory);
    }

    @Transactional
    public void deleteProfile(Long id) {
        Profile categoryToDelete = findEntityById(id);
        profileRepository.delete(categoryToDelete);
    }

    public Profile findEntityById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("profile not found with id: " + id));
    }
}
