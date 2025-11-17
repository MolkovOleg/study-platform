package com.molkovor.studyplatform.dto.quiz;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuizAnswerPayload(
        @NotNull Long questionId,
        List<Long> selectedOptionIds
) {}