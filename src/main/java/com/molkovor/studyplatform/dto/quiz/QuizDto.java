package com.molkovor.studyplatform.dto.quiz;

public record QuizDto(
        Long id,
        String title,
        Long moduleId
) {
}
