package com.molkovor.studyplatform.controller;

import com.molkovor.studyplatform.dto.course.*;
import com.molkovor.studyplatform.entity.course.Course;
import com.molkovor.studyplatform.entity.course.Lesson;
import com.molkovor.studyplatform.entity.course.Module;
import com.molkovor.studyplatform.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseRestController {


    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createCourse(@Valid @RequestBody CourseCreateRequest request) {
        Course course = courseService.createCourse(
                request.title(),
                request.description(),
                request.categoryId(),
                request.teacherId()
        );

        return new CourseDto(course.getId(), course.getTitle(), course.getDescription());
    }

    @PostMapping("/{courseId}/modules")
    @ResponseStatus(HttpStatus.CREATED)
    public ModuleDto addModule(@PathVariable Long courseId,
                               @Valid @RequestBody ModuleCreateRequest request) {
        Module module = courseService.addModule(
                courseId,
                request.title(),
                request.orderIndex()
        );

        return new ModuleDto(module.getId(), module.getTitle(), module.getOrderIndex(), List.of());
    }

    @PostMapping("/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto addLesson(@PathVariable Long moduleId,
                               @Valid @RequestBody LessonCreateRequest request) {

        Lesson lesson = courseService.addLesson(
                moduleId,
                request.title(),
                request.content()
        );

        return new LessonDto(lesson.getId(), lesson.getTitle(), lesson.getContent());
    }

    @GetMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CourseDetailDto getDetailCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        List<ModuleDto> modules = course.getModules().stream()
                .map(module -> new ModuleDto(
                        module.getId(),
                        module.getTitle(),
                        module.getOrderIndex(),
                        module.getLessons().stream()
                                .map(lesson -> new LessonDto(
                                        lesson.getId(),
                                        lesson.getTitle(),
                                        lesson.getContent()
                                )).toList()))
                .toList();

        return new CourseDetailDto(course.getId(), course.getTitle(), course.getDescription(), modules);
    }

    @DeleteMapping("/courseId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseById(@PathVariable Long courseId) {
        courseService.deleteCourseById(courseId);
    }
}
