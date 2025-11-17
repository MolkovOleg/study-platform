package com.molkovor.studyplatform.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.molkovor.studyplatform.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true, exclude = "user")
@ToString(callSuper = true, exclude = "user")
@Entity
@Table(name = "profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    @NotNull
    private User user;

    @Column(name = "bio", nullable = false)
    @NotBlank
    private String bio;

    @Column(name = "avatar_url")
    private String avatarUrl;
}
