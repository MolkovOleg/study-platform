package com.molkovor.studyplatform.dto.quiz;

import jakarta.validation.constraints.NotNull;

public record QuizGradeRequest(
        @NotNull Long teacherId,
        Integer manualScore,
        Boolean passed,
        String comment
) {
}
