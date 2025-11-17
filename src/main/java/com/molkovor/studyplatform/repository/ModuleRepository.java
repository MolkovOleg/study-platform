package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.course.Module;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    @EntityGraph(attributePaths = "lessons")
    Optional<Module> findWithLessonsById(Long moduleId);
}
