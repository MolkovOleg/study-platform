package com.molkovor.studyplatform.service;

import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.exception.BadRequestException;
import com.molkovor.studyplatform.exception.ResourceNotFoundException;
import com.molkovor.studyplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User register(String name, String email, Role role) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("User with this email already exists");
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .role(role)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, String name, String email, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (name != null && !name.isBlank()) {
            user.setName(name);
        }

        if (email != null && !email.isBlank()) {
            if (!email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new BadRequestException("Email already exists");
            }
            user.setEmail(email);
        }
        if (role != null) {
            user.setRole(role);
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUserByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
