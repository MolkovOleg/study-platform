package com.molkovor.studyplatform.dto.course;

import jakarta.validation.constraints.NotBlank;

public record LessonCreateRequest(
        @NotBlank String title,
        @NotBlank String content
) {
}
