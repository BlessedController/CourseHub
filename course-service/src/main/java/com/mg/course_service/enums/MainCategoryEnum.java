package com.mg.course_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MainCategoryEnum {
    TECHNOLOGY("TECHNOLOGY"),
    BUSINESS("BUSINESS"),
    DESIGN("DESIGN"),
    PERSONAL_DEVELOPMENT("PERSONAL DEVELOPMENT"),
    MARKETING("MARKETING"),
    FINANCE("FINANCE"),
    HEALTH("HEALTH"),
    MUSIC("MUSIC"),
    PHOTOGRAPHY("PHOTOGRAPHY"),
    TEACHING("TEACHING"),
    LANGUAGE("LANGUAGE"),
    LIFESTYLE("LIFESTYLE"),
    IT_SOFTWARE("IT SOFTWARE"),
    DATA_SCIENCE("DATA SCIENCE"),
    OFFICE_PRODUCTIVITY("OFFICE PRODUCTIVITY");

    private final String displayName;

    MainCategoryEnum(String displayName) {
        this.displayName = displayName;
    }

@JsonValue
    public String getDisplayName() {
        return this.displayName;
    }



}
