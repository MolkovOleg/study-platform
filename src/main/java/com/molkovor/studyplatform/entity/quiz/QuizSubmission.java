package com.molkovor.studyplatform.entity.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import com.molkovor.studyplatform.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true, exclude = {
        "quiz", "student"
})
@ToString(callSuper = true, exclude = {
        "quiz", "student"
})
@Entity
@Table(name = "quiz_submissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizSubmission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    @NotNull
    private User student;

    @Column(name = "score", nullable = false)
    @NotNull
    private Integer score;

    @Column(name = "taken_at", nullable = false)
    @NotNull
    private Instant takenAt = Instant.now();
}
