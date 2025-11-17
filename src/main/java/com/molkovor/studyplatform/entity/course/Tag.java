package com.molkovor.studyplatform.entity.course;

import com.molkovor.studyplatform.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "courses")
@ToString(callSuper = true, exclude = "courses")
@Entity
@Table(name = "tags")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag extends BaseEntity {

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Course> courses = new HashSet<>();

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    private String name;
}
