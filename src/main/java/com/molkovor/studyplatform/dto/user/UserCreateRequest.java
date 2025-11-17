package com.molkovor.studyplatform.dto.user;

import com.molkovor.studyplatform.entity.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

public record UserCreateRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email String email,
        @NonNull Role role
) {
}
