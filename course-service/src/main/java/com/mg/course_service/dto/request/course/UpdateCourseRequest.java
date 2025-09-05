package com.mg.course_service.dto.request.course;

import com.mg.course_service.dto.CategoryDTO;
import com.mg.course_service.validation.UniqueCourseTitle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record UpdateCourseRequest(

        @Pattern(regexp = "^[^\\\\/:*?\"<>|]+$", message = "Title cannot contain special characters: \\ / : * ? \" < > |")
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        @UniqueCourseTitle
        String title,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price cannot be negative")
        Double price,

        @NotNull(message = "Please specify whether the item is published")
        Boolean isPublished,

        @Valid
        @NotNull(message = "Categories cannot be null")
        @Size(min = 1, message = "At least one category and subcategory is required")
        List<@Valid CategoryDTO> categories

) {
}
