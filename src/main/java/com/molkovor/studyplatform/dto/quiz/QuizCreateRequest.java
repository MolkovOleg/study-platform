package com.molkovor.studyplatform.dto.quiz;

import jakarta.validation.constraints.NotBlank;

public record QuizCreateRequest(
        @NotBlank String title
) {
}
