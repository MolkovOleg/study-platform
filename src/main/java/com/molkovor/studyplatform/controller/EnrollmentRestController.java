package com.molkovor.studyplatform.controller;

import com.molkovor.studyplatform.dto.course.CourseDto;
import com.molkovor.studyplatform.dto.enrollment.EnrollRequest;
import com.molkovor.studyplatform.dto.enrollment.EnrollmentDto;
import com.molkovor.studyplatform.dto.user.UserDto;
import com.molkovor.studyplatform.entity.enrollment.Enrollment;
import com.molkovor.studyplatform.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/enrollments")
class EnrollmentRestController {


    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentDto enroll(@Valid @RequestBody EnrollRequest request) {
        Enrollment enrollment = enrollmentService.enroll(request.courseId(), request.studentId());

        return new EnrollmentDto(
                enrollment.getId(),
                enrollment.getUser().getId(),
                enrollment.getCourse().getId(),
                enrollment.getEnrollDate()
        );
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unenroll(@Valid @RequestBody EnrollRequest request) {
        enrollmentService.unenroll(request.courseId(), request.studentId());
    }

    @GetMapping("/students/by-courseId/{courseId}")
    public List<UserDto> getStudentsByCourseId(@PathVariable("courseId") Long courseId) {
        return enrollmentService.getStudentsByCourseId(courseId).stream()
                .map(student -> new UserDto(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getRole()
                )).toList();
    }

    @GetMapping("/courses/by-studentId/{studentId}")
    public List<CourseDto> getCoursesByStudentId(@PathVariable("studentId") Long studentId) {
        return enrollmentService.getCoursesByStudentId(studentId).stream()
                .map(course -> new CourseDto(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription()
                )).toList();
    }
}
