package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.quiz.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    boolean existsByQuizIdAndStudentId(Long quizId, Long studentId);

    List<QuizSubmission> findByQuizId(Long quizId);

    List<QuizSubmission> findByStudentId(Long studentId);
}
