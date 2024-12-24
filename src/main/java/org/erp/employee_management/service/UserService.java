package org.erp.employee_management.service;

import lombok.RequiredArgsConstructor;
import org.erp.employee_management.DTO.UserRequest;
import org.erp.employee_management.DTO.UserResponse;
import org.erp.employee_management.exception.ValidationException;
import org.erp.employee_management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ValidationException("Username already exists");
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.create(userRequest);
    }

    public Optional<UserResponse> updateUser(String username, UserRequest userRequest) {
        if (!userRepository.existsByUsername(username)) {
            throw new ValidationException("Username does not exist");
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.update(username, userRequest);
    }

    public Boolean deleteUser(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new ValidationException("Username does not exist");
        }
        return userRepository.delete(username);
    }


}
