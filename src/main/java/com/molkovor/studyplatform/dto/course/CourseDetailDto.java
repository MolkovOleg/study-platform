package com.molkovor.studyplatform.dto.course;

import java.util.List;

public record CourseDetailDto(
        Long id,
        String title,
        String description,
        List<ModuleDto> modules
) {
}
