package com.molkovor.studyplatform.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true, exclude = "course")
@ToString(callSuper = true, exclude = "course")
@Entity
@Table(name = "schedules")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Schedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Course course;

    @Column(name = "start_date", nullable = false)
    @NotNull
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @NotNull
    private LocalDate endDate;

    @Column(name = "weekday", nullable = false)
    @NotBlank
    private String weekday;
}
