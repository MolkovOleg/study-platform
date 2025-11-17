package com.molkovor.studyplatform.controller;

import com.molkovor.studyplatform.dto.study.AssignmentCreateRequest;
import com.molkovor.studyplatform.dto.study.AssignmentDto;
import com.molkovor.studyplatform.dto.study.SubmissionDto;
import com.molkovor.studyplatform.dto.study.SubmissionRequest;
import com.molkovor.studyplatform.entity.study.Assignment;
import com.molkovor.studyplatform.entity.study.Submission;
import com.molkovor.studyplatform.service.AssignmentService;
import com.molkovor.studyplatform.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
class AssignmentRestController {


    private final AssignmentService assignmentService;
    private final SubmissionService submissionService;

    @PostMapping
    public AssignmentDto createAssignment(
            @PathVariable Long lessonId,
            @PathVariable Long teacherId,
            @Valid @RequestBody AssignmentCreateRequest request) {

        Assignment assignment = assignmentService.createAssignment(
                lessonId,
                teacherId,
                request.title(),
                request.description(),
                request.dueDate());

        return new AssignmentDto(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getDueDate()
        );
    }

    @PostMapping("/{assignmentId}/submissions")
    public SubmissionDto submitAssignment(
            @PathVariable Long assignmentId,
            @Valid @RequestBody SubmissionRequest request) {

        Submission submission = submissionService.submitAssignment(
                request.studentId(), assignmentId, request.content());

        return new SubmissionDto(
                submission.getId(),
                submission.getAssignment().getId(),
                submission.getStudent().getId(),
                submission.getScore(),
                submission.getFeedback(),
                submission.getSubmittedAt());
    }

    @GetMapping("/by-lessonId/{lessonId}")
    public List<AssignmentDto> getAssignmentsByLessonId(@PathVariable Long lessonId) {
        return assignmentService.getAssignmentsByLessonId(lessonId).stream()
                .map(assignment -> new AssignmentDto(
                        assignment.getId(),
                        assignment.getTitle(),
                        assignment.getDescription(),
                        assignment.getDueDate()))
                .toList();
    }

    @GetMapping("/submissions/{assignmentId}")
    public List<SubmissionDto> getSubmissions(@PathVariable Long assignmentId) {
        return submissionService.getSubmissionsByAssignmentId(assignmentId).stream()
                .map(submission -> new SubmissionDto(
                        submission.getId(),
                        submission.getAssignment().getId(),
                        submission.getStudent().getId(),
                        submission.getScore(),
                        submission.getFeedback(),
                        submission.getSubmittedAt()))
                .toList();
    }

    @GetMapping("/submissions/by-studentId/{studentId}")
    public List<SubmissionDto> getSubmissionsByStudentId(@PathVariable Long studentId) {
        return submissionService.getSubmissionsByStudentId(studentId).stream()
                .map(submission -> new SubmissionDto(
                        submission.getId(),
                        submission.getAssignment().getId(),
                        submission.getStudent().getId(),
                        submission.getScore(),
                        submission.getFeedback(),
                        submission.getSubmittedAt()))
                .toList();
    }
}
