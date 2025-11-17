package com.molkovor.studyplatform.entity.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import com.molkovor.studyplatform.entity.course.Module;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {
        "module", "questions", "quizSubmissions"
})
@ToString(callSuper = true, exclude = {
        "module", "questions", "quizSubmissions"
})
@Entity
@Table(name = "quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quiz extends BaseEntity {

    @Column(name = "title", nullable = false)
    @NotBlank
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false, unique = true)
    @JsonIgnore
    @NotNull
    private Module module;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<QuizSubmission> quizSubmissions = new ArrayList<>();
}
