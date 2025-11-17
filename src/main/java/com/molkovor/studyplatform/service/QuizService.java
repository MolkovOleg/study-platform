package com.molkovor.studyplatform.service;

import com.molkovor.studyplatform.entity.course.Module;
import com.molkovor.studyplatform.entity.quiz.AnswerOption;
import com.molkovor.studyplatform.entity.quiz.Question;
import com.molkovor.studyplatform.entity.quiz.Quiz;
import com.molkovor.studyplatform.entity.quiz.QuizSubmission;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.exception.BadRequestException;
import com.molkovor.studyplatform.exception.ResourceNotFoundException;
import com.molkovor.studyplatform.repository.*;
import com.molkovor.studyplatform.dto.quiz.QuizAnswerPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final ModuleRepository moduleRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final UserRepository userRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;

    @Transactional
    public Quiz createQuiz(Long modelId,
                           String title) {
        if (quizRepository.findByModuleId(modelId).isPresent()) {
            throw new BadRequestException("Quiz with id " + modelId + " already exists");
        }

        Module module = moduleRepository.findById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", modelId));

        Quiz quiz = Quiz.builder()
                .module(module)
                .title(title)
                .build();

        return quizRepository.save(quiz);
    }

    @Transactional
    public Question addQuestion(Long quizId,
                                String text,
                                String type,
                                List<AnswerOption> options) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizId));

        Question question = Question.builder()
                .quiz(quiz)
                .text(text)
                .type(type)
                .build();

        Question savedQuestion = questionRepository.save(question);

        List<AnswerOption> savedOptions = options.stream()
                .map(opt -> answerOptionRepository.save(
                        AnswerOption.builder()
                                .question(savedQuestion)
                                .text(opt.getText())
                                .isCorrect(opt.getIsCorrect())
                                .build()
                ))
                .toList();

        savedQuestion.setAnswerOptions(savedOptions);

        return savedQuestion;
    }

    @Transactional
    public QuizSubmission submitQuiz(Long studentId,
                                     Long quizId,
                                     List<QuizAnswerPayload> answers) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", studentId));

        if (student.getRole() != Role.STUDENT) {
            throw new BadRequestException("Only students can submit quizzes");
        }

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizId));

        if (quizSubmissionRepository.existsByQuizIdAndStudentId(quizId, studentId)) {
            throw new BadRequestException("Quiz with id " + quizId + " already taken by student " + studentId);
        }

        Map<Long, List<Long>> answerMap = answers.stream()
                .collect(Collectors.toMap(
                        QuizAnswerPayload::questionId,
                        QuizAnswerPayload::selectedOptionIds));

        List<Question> questions = questionRepository.findByQuizId(quizId);
        int correctAnswers = 0;
        for (Question question : questions) {
            List<Long> selected = answerMap.getOrDefault(question.getId(), List.of());
            List<Long> expected = question.getAnswerOptions().stream()
                    .filter(AnswerOption::getIsCorrect)
                    .map(AnswerOption::getId)
                    .toList();

            if (selected.size() == expected.size() && selected.containsAll(expected)) {
                correctAnswers++;
            }
        }

        int scorePercentage = questions.isEmpty() ? 0 : correctAnswers * 100 / questions.size();

        QuizSubmission quizSubmission = QuizSubmission.builder()
                .quiz(quiz)
                .student(student)
                .score(scorePercentage)
                .takenAt(Instant.now())
                .build();

        return quizSubmissionRepository.save(quizSubmission);
    }

    @Transactional(readOnly = true)
    public List<QuizSubmission> getAllQuizSubmissionsByQuizId(Long quizId) {
        quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizId));
        return quizSubmissionRepository.findByQuizId(quizId);
    }

    @Transactional(readOnly = true)
    public List<QuizSubmission> getAllQuizSubmissionsByStudentId(Long studentId) {
        userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Stdent", "id", studentId));
        return quizSubmissionRepository.findByStudentId(studentId);
    }
}
