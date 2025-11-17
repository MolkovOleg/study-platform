package com.molkovor.studyplatform.dto.quiz;

import java.time.Instant;

public record QuizSubmissionDto(
        Long id,
        Long quizId,
        Long studentId,
        Integer score,
        Instant takenAt
) {
}
