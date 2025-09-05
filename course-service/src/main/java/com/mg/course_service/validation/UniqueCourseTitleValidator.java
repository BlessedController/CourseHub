package com.mg.course_service.validation;

import com.mg.course_service.service.CourseService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueCourseTitleValidator implements ConstraintValidator<UniqueCourseTitle, String> {
    private final CourseService courseService;

    public UniqueCourseTitleValidator(CourseService courseService) {
        this.courseService = courseService;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !courseService.existsByTitle(value);
    }

}
