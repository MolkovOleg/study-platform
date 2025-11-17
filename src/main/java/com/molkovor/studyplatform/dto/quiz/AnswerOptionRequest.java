package com.molkovor.studyplatform.dto.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerOptionRequest(
        @NotBlank String text,
        @NotNull Boolean isCorrect
) {
}

