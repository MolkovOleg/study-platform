package com.molkovor.studyplatform.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    protected Instant updatedAt = Instant.now();

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = Instant.now();
    }

}
