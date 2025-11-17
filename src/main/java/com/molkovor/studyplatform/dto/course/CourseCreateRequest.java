package com.molkovor.studyplatform.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseCreateRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull Long categoryId,
        @NotNull Long teacherId
) {
}
