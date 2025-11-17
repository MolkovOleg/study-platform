package com.molkovor.studyplatform.entity.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {
        "quiz", "answerOptions"
})
@ToString(callSuper = true, exclude = {
        "quiz", "answerOptions"
})
@Entity
@Table(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Quiz quiz;

    @Column(name = "text", nullable = false, length = 4000)
    @NotBlank
    @Size(max = 4000)
    private String text;

    @Column(name = "type", nullable = false)
    @NotBlank
    private String type;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<AnswerOption> answerOptions = new ArrayList<>();
}
