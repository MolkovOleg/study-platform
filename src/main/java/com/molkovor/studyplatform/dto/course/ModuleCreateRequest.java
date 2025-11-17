package com.molkovor.studyplatform.dto.course;

import jakarta.validation.constraints.NotBlank;

public record ModuleCreateRequest(
        @NotBlank String title,
        Integer orderIndex
) {
}
