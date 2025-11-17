package com.molkovor.studyplatform.dto.course;

import com.molkovor.studyplatform.entity.course.Lesson;

import java.util.List;

public record ModuleDto(
        Long id,
        String title,
        Integer orderIndex,
        List<LessonDto> lessons
) {
}
