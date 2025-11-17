package com.molkovor.studyplatform.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import com.molkovor.studyplatform.entity.study.Assignment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {
        "assignments", "module"
})
@ToString(callSuper = true, exclude = {
        "assignments", "module"
})
@Entity
@Table(name = "lessons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Module module;

    @Column(name = "title", nullable = false)
    @NotBlank
    private String title;

    @Column(name = "content", nullable = false, length = 10000)
    @NotBlank
    @Size(max = 10000)
    private String content;

    @Column(name = "video_url")
    private String videoUrl;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Assignment> assignments = new ArrayList<>();
}
