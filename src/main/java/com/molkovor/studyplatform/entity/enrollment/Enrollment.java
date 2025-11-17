package com.molkovor.studyplatform.entity.enrollment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true, exclude = {
        "user", "course"
})
@ToString(callSuper = true, exclude = {
        "user", "course"
})
@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "course_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Course course;

    @Column(name = "enroll_date", nullable = false)
    @Builder.Default
    @NotNull
    private Instant enrollDate = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    @NotNull
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;
}
