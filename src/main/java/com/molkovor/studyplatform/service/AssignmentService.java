package com.molkovor.studyplatform.service;

import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.course.Lesson;
import com.molkovor.studyplatform.entity.study.Assignment;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.exception.BadRequestException;
import com.molkovor.studyplatform.exception.ResourceNotFoundException;
import com.molkovor.studyplatform.repository.AssignmentRepository;
import com.molkovor.studyplatform.repository.LessonRepository;
import com.molkovor.studyplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final LessonRepository lessonRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Assignment createAssignment(Long lessonId, Long teacherId, String title, String description, Instant dueDate) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));
        Course course = lesson.getModule().getCourse();

        if (teacher.getRole() != Role.TEACHER || !course.getTeacher().getId().equals(teacherId)) {
            throw new BadRequestException("Only teacher can create assignments!");
        }

        Assignment assignment = Assignment.builder()
                .lesson(lesson)
                .title(title)
                .description(description)
                .dueDate(dueDate)
                .build();

        return assignmentRepository.save(assignment);
    }

    @Transactional(readOnly = true)
    public List<Assignment> getAssignmentsByLessonId(Long lessonId) {
        if (lessonRepository.findById(lessonId).isPresent()) {
            return assignmentRepository.findByLessonId(lessonId);
        } else {
            throw new ResourceNotFoundException("Lesson", "id", lessonId);
        }
    }


}
