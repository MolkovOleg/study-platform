package com.molkovor.studyplatform.dto.quiz;

import java.util.List;

public record QuestionDto(
        Long id,
        String text,
        String type,
        List<AnswerOptionDto> options
) {
}

