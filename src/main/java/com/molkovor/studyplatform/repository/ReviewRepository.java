package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.course.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findByCourseIdAndStudentId(Long courseId, Long studentId);

    List<Review> findByCourseId(Long courseId);

    List<Review> findByStudentId(Long studentId);
}
