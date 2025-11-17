package com.molkovor.studyplatform.dto.quiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record QuestionCreateRequest(
        @NotBlank String text,
        @NotBlank String type,
        @NotEmpty List<@Valid AnswerOptionRequest> options
) {
}

