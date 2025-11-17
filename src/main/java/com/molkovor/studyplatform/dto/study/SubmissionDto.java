package com.molkovor.studyplatform.dto.study;

import java.time.Instant;

public record SubmissionDto(
        Long id,
        Long assignmentId,
        Long studentId,
        Integer score,
        String feedback,
        Instant submittedAt
) {
}
