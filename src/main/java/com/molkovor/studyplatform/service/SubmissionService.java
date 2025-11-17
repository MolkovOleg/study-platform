package com.molkovor.studyplatform.service;

import com.molkovor.studyplatform.entity.study.Submission;
import com.molkovor.studyplatform.entity.study.Assignment;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.exception.BadRequestException;
import com.molkovor.studyplatform.exception.ResourceNotFoundException;
import com.molkovor.studyplatform.repository.AssignmentRepository;
import com.molkovor.studyplatform.repository.SubmissionRepository;
import com.molkovor.studyplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional
    public Submission submitAssignment(Long studentId,
                                       Long assignmentId,
                                       String content) {

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        if (student.getRole() != Role.STUDENT) {
            throw new BadRequestException("Only students can submit assignments!");
        }

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", assignmentId));

        if (submissionRepository.existsByAssignmentIdAndStudentId(assignmentId, studentId)) {
            throw new BadRequestException("Assignment already exits!");
        }

        Submission submission = Submission.builder()
                .assignment(assignment)
                .student(student)
                .content(content)
                .build();

        return submissionRepository.save(submission);
    }

    @Transactional
    public Submission gradeSubmission(Long submissionId,
                                      Long teacherId,
                                      Integer score,
                                      String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission", "id", submissionId));
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));

        if (teacher.getRole() != Role.TEACHER) {
            throw new BadRequestException("Only teachers can grade assignments!");
        }

        submission.setScore(score);
        submission.setFeedback(feedback);

        return submissionRepository.save(submission);
    }

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByAssignmentId(Long assignmentId) {
        if (assignmentRepository.existsById(assignmentId)) {
            return submissionRepository.findByAssignmentId(assignmentId);
        } else {
            throw new ResourceNotFoundException("Assignment", "id", assignmentId);
        }
    }

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByStudentId(Long studentId) {
        if (userRepository.existsById(studentId)) {
            return submissionRepository.findByStudentId(studentId);
        } else {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }
    }

}
