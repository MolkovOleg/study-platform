package com.molkovor.studyplatform.dto.study;

import java.time.Instant;

public record AssignmentDto(
        Long id,
        String title,
        String description,
        Instant dueDate
) {
}
