package com.molkovor.studyplatform.dto.quiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuizTakeRequest(
        @NotNull Long studentId,
        @NotEmpty List<@Valid QuizAnswerPayload> answers
) {
}

