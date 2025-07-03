package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.UserResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import com.example.java_ifortex_test_task.mapper.UserMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import com.example.java_ifortex_test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;

    // Returns a User with the biggest amount of sessions
    public UserResponseDTO getUserWithMostSessions() {
        User user = userRepository.getUserWithMostSessions();
        return userMapper.toDto(user);
    }

    // Returns Users that have at least 1 Mobile session
    public List<UserResponseDTO> getUsersWithAtLeastOneMobileSession() {
        List<User> users = userRepository.getUsersWithAtLeastOneMobileSession(DeviceType.MOBILE);
        List<UserResponseDTO> responseDTOS = new ArrayList<>(users.size());
        for (User user : users) {
            responseDTOS.add(userMapper.toDto(user));
        }
        return responseDTOS;
    }
}
