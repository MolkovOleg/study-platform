package com.molkovor.studyplatform.service;

import com.molkovor.studyplatform.entity.course.Category;
import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.course.Lesson;
import com.molkovor.studyplatform.entity.course.Module;
import com.molkovor.studyplatform.entity.user.User;
import com.molkovor.studyplatform.exception.BadRequestException;
import com.molkovor.studyplatform.exception.ResourceNotFoundException;
import com.molkovor.studyplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;


    @Transactional
    public Course createCourse(String title, String description, Long categoryId, Long teacherId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));

        Course course = Course.builder()
                .title(title)
                .description(description)
                .category(category)
                .teacher(teacher)
                .build();

        return courseRepository.save(course);
    }

    @Transactional
    public Module addModule(Long courseId, String title, Integer orderIndex) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Module module = Module.builder()
                .course(course)
                .title(title)
                .orderIndex(orderIndex)
                .build();

        return moduleRepository.save(module);
    }

    @Transactional
    public Lesson addLesson(Long moduleId, String title, String content) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", moduleId));

        Lesson lesson = Lesson.builder()
                .module(module)
                .title(title)
                .content(content)
                .build();

        return lessonRepository.save(lesson);
    }


    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
    }

    @Transactional(readOnly = true)
    public Course getCourseWithFullStructureById(Long courseId) {
        return courseRepository.findWithModulesAndLessonsById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
    }

    @Transactional(readOnly = true)
    public Module getModuleById(Long moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", moduleId));
    }

    @Transactional(readOnly = true)
    public Module getModuleWithLessonsById(Long moduleId) {
        return moduleRepository.findWithLessonsById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", moduleId));
    }

    @Transactional(readOnly = true)
    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));
    }

    @Transactional
    public void deleteCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        if (!course.getEnrollments().isEmpty()) {
            throw new BadRequestException("Cannot delete course with active enrollments!");
        }

        courseRepository.delete(course);
    }

    @Transactional
    public void deleteModuleById(Long moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new ResourceNotFoundException("Module", "id", moduleId);
        }
        moduleRepository.deleteById(moduleId);
    }

    @Transactional
    public void deleteLessonById(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson", "id", lessonId);
        }
        lessonRepository.deleteById(lessonId);
    }
}
