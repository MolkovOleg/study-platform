package com.molkovor.studyplatform.dto.enrollment;

import java.time.Instant;

public record EnrollmentDto(
        Long id,
        Long studentId,
        Long courseId,
        Instant enrollDate
) {
}
