package com.molkovor.studyplatform.controller;

import com.molkovor.studyplatform.dto.quiz.*;
import com.molkovor.studyplatform.entity.quiz.AnswerOption;
import com.molkovor.studyplatform.entity.quiz.Question;
import com.molkovor.studyplatform.entity.quiz.Quiz;
import com.molkovor.studyplatform.entity.quiz.QuizSubmission;
import com.molkovor.studyplatform.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quizzes")
class QuizRestController {

    private final QuizService quizService;

    @PostMapping("/modules/{moduleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public QuizDto createQuiz(@PathVariable Long moduleId,
                              @Valid @RequestBody QuizCreateRequest request) {
        Quiz quiz = quizService.createQuiz(moduleId, request.title());
        return new QuizDto(quiz.getId(), quiz.getTitle(), quiz.getModule().getId());
    }

    @PostMapping("/{quizId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto addQuestion(@PathVariable Long quizId,
                                   @Valid @RequestBody QuestionCreateRequest request) {

        List<AnswerOption> options = request.options().stream()
                .map(opt -> AnswerOption.builder()
                        .text(opt.text())
                        .isCorrect(opt.isCorrect())
                        .build())
                .toList();

        Question question = quizService.addQuestion(quizId, request.text(), request.type(), options);

        List<AnswerOptionDto> optionDtos = question.getAnswerOptions().stream()
                .map(opt -> new AnswerOptionDto(opt.getId(), opt.getText(), opt.getIsCorrect()))
                .toList();

        return new QuestionDto(question.getId(), question.getText(), question.getType(), optionDtos);
    }

    @PostMapping("/{quizId}/submissions")
    @ResponseStatus(HttpStatus.CREATED)
    public QuizSubmissionDto submitQuiz(@PathVariable Long quizId,
                                        @Valid @RequestBody QuizTakeRequest request) {
        QuizSubmission submission = quizService.submitQuiz(request.studentId(), quizId, request.answers());
        return toSubmissionDto(submission);
    }

    @GetMapping("/{quizId}/submissions")
    public List<QuizSubmissionDto> getSubmissionsForQuiz(@PathVariable Long quizId) {
        return quizService.getAllQuizSubmissionsByQuizId(quizId).stream()
                .map(this::toSubmissionDto)
                .toList();
    }

    @GetMapping("/submissions/by-student/{studentId}")
    public List<QuizSubmissionDto> getSubmissionsForStudent(@PathVariable Long studentId) {
        return quizService.getAllQuizSubmissionsByStudentId(studentId).stream()
                .map(this::toSubmissionDto)
                .toList();
    }

    private QuizSubmissionDto toSubmissionDto(QuizSubmission submission) {
        return new QuizSubmissionDto(
                submission.getId(),
                submission.getQuiz().getId(),
                submission.getStudent().getId(),
                submission.getScore(),
                submission.getTakenAt()
        );
    }
}
