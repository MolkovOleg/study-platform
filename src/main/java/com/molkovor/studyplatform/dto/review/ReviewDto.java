package com.molkovor.studyplatform.dto.review;

public record ReviewDto(
        Long id,
        Long courseId,
        Long studentId,
        Integer rating,
        String comment
) {
}
