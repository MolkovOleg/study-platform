package com.molkovor.studyplatform.dto.quiz;

public record AnswerOptionDto(
        Long id,
        String text,
        Boolean isCorrect
) {
}

