package com.molkovor.studyplatform.entity.study;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import com.molkovor.studyplatform.entity.course.Lesson;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {
        "lesson", "submissions"
})
@ToString(callSuper = true, exclude = {
        "lesson", "submissions"
})
@Entity
@Table(name = "assignments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assignment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Lesson lesson;

    @Column(name = "title", nullable = false)
    @NotBlank
    private String title;

    @Column(name = "description", nullable = false, length = 8000)
    @NotBlank
    @Size(max = 8000)
    private String description;

    @Column(name = "due_date", nullable = false)
    @NotNull
    private Instant dueDate;

    @Column(name = "max_score")
    private Integer maxScore = 100;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<Submission> submissions = new ArrayList<>();
}
