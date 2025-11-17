package com.molkovor.studyplatform.controller;

import com.molkovor.studyplatform.dto.user.UserCreateRequest;
import com.molkovor.studyplatform.dto.user.UserDto;
import com.molkovor.studyplatform.dto.user.UserUpdateRequest;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.exception.ResourceNotFoundException;
import com.molkovor.studyplatform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
class UserRestController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody UserCreateRequest request) {
        User user = userService.register(request.name(), request.email(), request.role());
        return toDto(user);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id,
                              @Valid @RequestBody UserUpdateRequest request) {
        User user = userService.updateUser(id, request.name(), request.email(), request.role());
        return toDto(user);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(required = false) Role role) {
        List<User> users = role == null ? userService.getAllUsers() : userService.getUserByRole(role);
        return users.stream()
                .map(this::toDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
