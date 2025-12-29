package com.example.ORM_project.service;

import com.example.ORM_project.database.entity.User;
import com.example.ORM_project.database.repository.UserRepository;
import com.example.ORM_project.dto.UserRequestDto;
import com.example.ORM_project.dto.UserResponseDto;
import com.example.ORM_project.exeptions.DuplicateException;
import com.example.ORM_project.exeptions.NotFoundException;
import com.example.ORM_project.mapper.UserMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email " + request.getEmail() + " already exists.");
        }
        User newUser = userMapper.toEntity(request);
        User savedUser = userRepository.save(newUser);
        return userMapper.toResponseDto(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = findEntityById(id);
        return userMapper.toResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto request) {
        User existingUser = findEntityById(id);
        userMapper.update(request, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User userToDelete = findEntityById(id);
        userRepository.delete(userToDelete);
    }

    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }
}
