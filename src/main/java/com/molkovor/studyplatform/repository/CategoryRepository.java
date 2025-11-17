package com.molkovor.studyplatform.repository;

import com.molkovor.studyplatform.entity.course.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
