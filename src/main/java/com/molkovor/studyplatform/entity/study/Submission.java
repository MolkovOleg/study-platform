package com.molkovor.studyplatform.entity.study;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import com.molkovor.studyplatform.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true, exclude = {
        "assignment", "student"
})
@ToString(callSuper = true, exclude = {
        "assignment", "student"
})
@Entity
@Table(name = "submissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"assignment_id", "student_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Submission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    @NotNull
    private User student;

    @Column(name = "submitted_at", nullable = false)
    @NotNull
    private Instant submittedAt = Instant.now();

    @Column(name = "content", nullable = false, length = 10000)
    @NotBlank
    private String content;

    @Column(name = "score")
    private Integer score;

    @Column(name = "feedback", nullable = false)
    @NotBlank
    private String feedback;
}
