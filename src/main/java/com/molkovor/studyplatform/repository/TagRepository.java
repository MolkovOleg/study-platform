package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.course.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
