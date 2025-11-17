package com.molkovor.studyplatform.integration;

import com.molkovor.studyplatform.dto.quiz.QuizAnswerPayload;
import com.molkovor.studyplatform.entity.course.Category;
import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.course.Module;
import com.molkovor.studyplatform.entity.quiz.AnswerOption;
import com.molkovor.studyplatform.entity.quiz.Question;
import com.molkovor.studyplatform.entity.quiz.Quiz;
import com.molkovor.studyplatform.entity.quiz.QuizSubmission;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.repository.CategoryRepository;
import com.molkovor.studyplatform.repository.UserRepository;
import com.molkovor.studyplatform.service.CourseService;
import com.molkovor.studyplatform.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class QuizIntegrationTest {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void studentSubmissionCalculatesScore() {
        Category category = categoryRepository.save(Category.builder()
                .name("Science")
                .build());

        User teacher = userRepository.save(User.builder()
                .name("Teacher Eve")
                .email("teacher3@example.com")
                .role(Role.TEACHER)
                .build());

        User student = userRepository.save(User.builder()
                .name("Student Dan")
                .email("student2@example.com")
                .role(Role.STUDENT)
                .build());

        Course course = courseService.createCourse(
                "Physics",
                "Mechanics",
                category.getId(),
                teacher.getId()
        );

        Module module = courseService.addModule(course.getId(), "Kinematics", 1);

        Quiz quiz = quizService.createQuiz(module.getId(), "Module Quiz");

        List<AnswerOption> options = List.of(
                AnswerOption.builder().text("9.8 m/s^2").isCorrect(true).build(),
                AnswerOption.builder().text("42 m/s^2").isCorrect(false).build()
        );

        Question question = quizService.addQuestion(
                quiz.getId(),
                "Approximate gravity on Earth?",
                "SINGLE_CHOICE",
                options
        );

        Long correctOptionId = question.getAnswerOptions().stream()
                .filter(AnswerOption::getIsCorrect)
                .findFirst()
                .map(AnswerOption::getId)
                .orElseThrow();

        QuizSubmission submission = quizService.submitQuiz(
                student.getId(),
                quiz.getId(),
                List.of(new QuizAnswerPayload(question.getId(), List.of(correctOptionId)))
        );

        assertThat(submission.getScore()).isEqualTo(100);
        assertThat(submission.getQuiz().getId()).isEqualTo(quiz.getId());
        assertThat(submission.getStudent().getId()).isEqualTo(student.getId());
    }
}

