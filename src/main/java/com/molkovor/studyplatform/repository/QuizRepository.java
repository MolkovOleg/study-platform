package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByModuleId(Long modelId);
}
