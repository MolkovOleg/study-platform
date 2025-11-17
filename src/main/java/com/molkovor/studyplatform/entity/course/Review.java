package com.molkovor.studyplatform.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import com.molkovor.studyplatform.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true, exclude = {
        "course", "student"
})
@ToString(callSuper = true, exclude = {
        "course", "student"
})
@Entity
@Table(name = "course_reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    @NotNull
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    @NotNull
    private User student;

    @Column(name = "rating", nullable = false)
    @NotNull
    @Min(1)
    @Max(10)
    private Integer rating;

    @Column(name = "comment")
    @NotBlank
    private String comment;
}
