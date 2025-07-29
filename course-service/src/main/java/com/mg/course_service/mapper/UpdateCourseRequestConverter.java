package com.mg.course_service.mapper;

import com.mg.course_service.dto.request.course.UpdateCourseRequest;
import com.mg.course_service.model.Category;
import com.mg.course_service.model.Course;
import com.mg.course_service.validator.CourseValidator;

import java.util.Set;
import java.util.stream.Collectors;

public final class UpdateCourseRequestConverter {
    private UpdateCourseRequestConverter() {
    }

    public static void updateEntity(UpdateCourseRequest request, Course course) {
        if (request.categories() != null && !request.categories().isEmpty()) {

            CourseValidator.validationCourseCategoriesSize(request.categories());
            CourseValidator.validateCategories(request.categories());

            Set<Category> newCategories = request.categories()
                    .stream()
                    .map(CategoryDTOConverter::toEntity)
                    .collect(Collectors.toSet());

            course.getCategories().clear();
            course.getCategories().addAll(newCategories);
        }

        if (request.title() != null) course.setTitle(request.title());
        if (request.description() != null) course.setDescription(request.description());
        if (request.price() != null) course.setPrice(request.price());
        if (request.isPublished() != null) course.setPublished(request.isPublished());
    }

}
