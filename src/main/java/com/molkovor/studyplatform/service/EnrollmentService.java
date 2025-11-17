package com.molkovor.studyplatform.service;

import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.enrollment.Enrollment;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.exception.BadRequestException;
import com.molkovor.studyplatform.exception.ResourceNotFoundException;
import com.molkovor.studyplatform.repository.CourseRepository;
import com.molkovor.studyplatform.repository.EnrollmentRepository;
import com.molkovor.studyplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public Enrollment enroll(Long userId, Long courseId) {
        User student = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (student.getRole() != Role.STUDENT) {
            throw new BadRequestException("Only students can enroll!");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BadRequestException("Student already enrolled!");
        }

        Enrollment enrollment = Enrollment.builder()
                .user(student)
                .course(course)
                .build();

        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void unenroll(Long userId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", courseId));

        enrollmentRepository.delete(enrollment);
    }

    @Transactional(readOnly = true)
    public List<User> getStudentsByCourseId(Long courseId) {

        if (courseRepository.existsById(courseId)) {
            return enrollmentRepository.findByCourseId(courseId).stream()
                    .map(Enrollment::getUser)
                    .toList();
        } else {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
    }

    @Transactional(readOnly = true)
    public List<Course> getCoursesByStudentId(Long studentId) {

        if (userRepository.existsById(studentId)) {
            return enrollmentRepository.findByUserId(studentId).stream()
                    .map(Enrollment::getCourse)
                    .toList();
        } else {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }
    }
}
