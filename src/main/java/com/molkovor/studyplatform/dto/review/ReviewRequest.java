package com.molkovor.studyplatform.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotNull Long courseId,
        @NotNull Long studentId,
        @NotNull @Min(1) @Max(5) Integer rating,
        String comment
) {
}
