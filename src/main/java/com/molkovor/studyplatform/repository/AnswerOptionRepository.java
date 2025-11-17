package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.quiz.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
}
