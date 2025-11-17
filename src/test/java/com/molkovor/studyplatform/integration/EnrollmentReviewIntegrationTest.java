package com.molkovor.studyplatform.integration;

import com.molkovor.studyplatform.entity.course.Category;
import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.course.Review;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.service.CourseService;
import com.molkovor.studyplatform.service.EnrollmentService;
import com.molkovor.studyplatform.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EnrollmentReviewIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private com.molkovor.studyplatform.repository.CategoryRepository categoryRepository;

    @Autowired
    private com.molkovor.studyplatform.repository.UserRepository userRepository;

    @Test
    void studentCanEnrollThenLeaveReview() {
        Category category = categoryRepository.save(Category.builder()
                .name("Math")
                .build());

        User teacher = userRepository.save(User.builder()
                .name("Teacher Bob")
                .email("teacher2@example.com")
                .role(Role.TEACHER)
                .build());

        User student = userRepository.save(User.builder()
                .name("Student Charlie")
                .email("student1@example.com")
                .role(Role.STUDENT)
                .build());

        Course course = courseService.createCourse(
                "Algebra",
                "Numbers and formulas",
                category.getId(),
                teacher.getId()
        );

        enrollmentService.enroll(student.getId(), course.getId());

        Review review = reviewService.create(student.getId(), course.getId(), 5, "Great course!");

        assertThat(review.getRating()).isEqualTo(5);
        assertThat(review.getComment()).isEqualTo("Great course!");
        assertThat(reviewService.getReviewByCourseId(course.getId())).hasSize(1);
        assertThat(reviewService.getReviewByStudentId(student.getId())).extracting(Review::getCourse)
                .containsExactly(course);
    }
}

