package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.course.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
