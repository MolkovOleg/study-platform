package com.molkovor.studyplatform.integration;

import com.molkovor.studyplatform.entity.course.Category;
import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.course.Lesson;
import com.molkovor.studyplatform.entity.course.Module;
import com.molkovor.studyplatform.entity.user.Role;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.repository.ModuleRepository;
import com.molkovor.studyplatform.repository.CategoryRepository;
import com.molkovor.studyplatform.repository.UserRepository;
import com.molkovor.studyplatform.service.CourseService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CourseIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void courseCanBeBuiltWithModulesAndLessons() {
        Category category = categoryRepository.save(Category.builder()
                .name("Programming")
                .build());

        User teacher = userRepository.save(User.builder()
                .name("Alice Teacher")
                .email("teacher@example.com")
                .role(Role.TEACHER)
                .build());

        Course course = courseService.createCourse(
                "Java Fundamentals",
                "Introductory java course",
                category.getId(),
                teacher.getId()
        );

        Module module = courseService.addModule(course.getId(), "Basics", 1);
        Lesson lesson = courseService.addLesson(module.getId(), "Hello World", "First steps with Java");

        entityManager.flush();
        entityManager.clear();

        Module storedModule = moduleRepository.findWithLessonsById(module.getId())
                .orElseThrow();

        assertThat(storedModule.getTitle()).isEqualTo("Basics");
        assertThat(storedModule.getLessons()).extracting(Lesson::getTitle)
                .containsExactly("Hello World");
        assertThat(lesson.getModule().getId()).isEqualTo(module.getId());
    }
}

