package com.mg.course_service.dto;

import com.mg.course_service.model.enums.CategoryEnum;
import com.mg.course_service.model.enums.MainCategoryEnum;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO(
        @NotNull(message = "Main category cannot be null")
        MainCategoryEnum mainCategory,

        @NotNull(message = "Sub category cannot be null")
        CategoryEnum category
) {


}
