package com.molkovor.studyplatform.entity.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true, exclude = "question")
@ToString(callSuper = true, exclude = "question")
@Entity
@Table(name = "answer_options")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerOption extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    @Column(name = "text", nullable = false)
    @NotBlank
    private String text;

    @Column(name = "is_correct", nullable = false)
    @NotNull
    private Boolean isCorrect;

}
