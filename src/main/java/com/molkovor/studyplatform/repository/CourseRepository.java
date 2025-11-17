package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.course.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = {"modules", "modules.lessons"})
    Optional<Course> findWithModulesAndLessonsById(Long courseId);
}
