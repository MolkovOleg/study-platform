package com.molkovor.studyplatform.dto.study;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AssignmentCreateRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull    Instant dueDate) {
}
