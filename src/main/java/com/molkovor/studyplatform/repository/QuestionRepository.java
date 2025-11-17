package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.quiz.Question;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @EntityGraph(attributePaths = "answerOptions")
    List<Question> findByQuizId(Long quizId);
}
