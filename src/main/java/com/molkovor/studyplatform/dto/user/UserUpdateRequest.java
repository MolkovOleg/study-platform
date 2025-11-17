package com.molkovor.studyplatform.dto.user;

import com.molkovor.studyplatform.entity.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(max = 100) String name,
        @Email String email,
        @NotNull Role role
) {
}
