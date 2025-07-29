package com.mg.course_service.dto.request.course;

import com.mg.course_service.dto.CategoryDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateCourseRequest(

        @Size(max = 150, message = "Title cannot exceed 150 characters")
        String title,

        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @DecimalMin(value = "0.0", message = "Price cannot be negative")
        Double price,

        Boolean isPublished,

        @Size(min = 1, message = "At least one category is required")
        List<CategoryDTO> categories

) {
}
