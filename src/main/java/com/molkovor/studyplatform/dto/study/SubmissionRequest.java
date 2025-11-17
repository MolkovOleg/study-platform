package com.molkovor.studyplatform.dto.study;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmissionRequest(
        @NotNull Long studentId,
        @NotBlank String content
) {
}
