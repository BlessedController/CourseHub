package com.mg.course_service.validator;

import com.mg.course_service.dto.CategoryDTO;
import com.mg.course_service.exception.AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException;
import com.mg.course_service.exception.CategoryLimitExceededException;
import com.mg.course_service.exception.CategoryMismatchException;
import com.mg.course_service.model.Course;
import com.mg.course_service.util.JwtUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class CourseValidator {

    public static final int MAX_CATEGORIES = 3;
    private final JwtUtil jwtUtil;

    public CourseValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public void validateInstructorItSelfOrAdmin(Course course, String token) {
        UUID userId = jwtUtil.getUserIdFromToken(token);
        UUID instructorId = course.getInstructorId();
        boolean isAdmin = jwtUtil.isAdmin(token);

        if (!isAdmin && !Objects.equals(userId, instructorId)) {
            throw new AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException(
                    "You are neither the course owner nor an admin"
            );
        }
    }

    public void validationCourseCategoriesSize(List<CategoryDTO> categoryDTOs) {
        if (categoryDTOs.size() > MAX_CATEGORIES) {
            throw new CategoryLimitExceededException("A course can have only " + MAX_CATEGORIES + " categories and them subcategories");
        }
    }

    public void validateCategories(List<CategoryDTO> dtos) {

        for (CategoryDTO dto : dtos) {
            var main = dto.mainCategory();
            var sub = dto.category();

            if (main == null) {
                continue;
            }

            if (sub == null) {
                continue;
            }

            if (sub.getCategoryGroup() == null) {
                continue;
            }

            if (!sub.getCategoryGroup().equals(main)) {
                throw new CategoryMismatchException(String.format(
                        "The selected subcategory %s does not belong to the main category %s",
                        sub.getDisplayName(),
                        main.getDisplayName()
                ));
            }
        }
    }
}
