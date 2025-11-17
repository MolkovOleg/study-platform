package com.molkovor.studyplatform.dto.enrollment;

import jakarta.validation.constraints.NotNull;

public record EnrollRequest(
        @NotNull Long studentId,
        @NotNull Long courseId) {
}
