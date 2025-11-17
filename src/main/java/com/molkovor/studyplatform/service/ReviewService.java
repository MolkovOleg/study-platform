package com.molkovor.studyplatform.service;

import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.course.Review;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.exception.BadRequestException;
import com.molkovor.studyplatform.exception.ResourceNotFoundException;
import com.molkovor.studyplatform.repository.CourseRepository;
import com.molkovor.studyplatform.repository.EnrollmentRepository;
import com.molkovor.studyplatform.repository.ReviewRepository;
import com.molkovor.studyplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public Review create(Long studentId,
                         Long courseId,
                         Integer rating,
                         String comment) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        if (student.getRole() != Role.STUDENT) {
            throw new BadRequestException("Only student can make reviews!");
        }

        if (!enrollmentRepository.existsByUserIdAndCourseId(studentId, courseId)) {
            throw new BadRequestException("Student is not enrolled in this course!");
        }

        Review review = reviewRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElseGet(() -> Review.builder()
                        .course(course)
                        .student(student)
                        .build());

        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewByCourseId(Long courseId) {
        return reviewRepository.findByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewByStudentId(Long studentId) {
        return reviewRepository.findByStudentId(studentId);
    }
}
