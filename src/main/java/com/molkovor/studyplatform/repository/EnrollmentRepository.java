package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.enrollment.Enrollment;
import com.molkovor.studyplatform.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findByUserId(Long userId);
}
