package com.molkovor.studyplatform.controller;

import com.molkovor.studyplatform.dto.review.ReviewDto;
import com.molkovor.studyplatform.dto.review.ReviewRequest;
import com.molkovor.studyplatform.entity.course.Review;
import com.molkovor.studyplatform.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
class ReviewRestController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto createReview(@Valid @RequestBody ReviewRequest request) {
        Review review = reviewService.create(
                request.studentId(),
                request.courseId(),
                request.rating(),
                request.comment()
        );
        return toDto(review);
    }

    @GetMapping("/by-course/{courseId}")
    public List<ReviewDto> getReviewsByCourse(@PathVariable Long courseId) {
        return reviewService.getReviewByCourseId(courseId).stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/by-student/{studentId}")
    public List<ReviewDto> getReviewsByStudent(@PathVariable Long studentId) {
        return reviewService.getReviewByStudentId(studentId).stream()
                .map(this::toDto)
                .toList();
    }

    private ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getCourse().getId(),
                review.getStudent().getId(),
                review.getRating(),
                review.getComment()
        );
    }
}

