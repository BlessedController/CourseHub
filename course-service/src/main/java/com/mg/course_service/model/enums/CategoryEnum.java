package com.mg.course_service.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.JoinColumn;

public enum CategoryEnum {

    // Technology
    PROGRAMMING("PROGRAMMING", MainCategoryEnum.TECHNOLOGY),
    WEB_DEVELOPMENT("WEB DEVELOPMENT", MainCategoryEnum.TECHNOLOGY),
    MOBILE_DEVELOPMENT("MOBILE DEVELOPMENT", MainCategoryEnum.TECHNOLOGY),
    DEVOPS("DEVOPS", MainCategoryEnum.TECHNOLOGY),
    CLOUD_COMPUTING("CLOUD COMPUTING", MainCategoryEnum.TECHNOLOGY),
    SOFTWARE_ENGINEERING("SOFTWARE ENGINEERING", MainCategoryEnum.TECHNOLOGY),
    NETWORK_SECURITY("NETWORK SECURITY", MainCategoryEnum.TECHNOLOGY),

    // IT & Software
    SYSTEM_ADMINISTRATION("SYSTEM ADMINISTRATION", MainCategoryEnum.IT_SOFTWARE),
    DATABASE_MANAGEMENT("DATABASE MANAGEMENT", MainCategoryEnum.IT_SOFTWARE),
    LINUX("LINUX", MainCategoryEnum.IT_SOFTWARE),

    // Data Science
    DATA_ANALYSIS("DATA ANALYSIS", MainCategoryEnum.DATA_SCIENCE),
    MACHINE_LEARNING("MACHINE LEARNING", MainCategoryEnum.DATA_SCIENCE),
    ARTIFICIAL_INTELLIGENCE("ARTIFICIAL INTELLIGENCE", MainCategoryEnum.DATA_SCIENCE),
    DEEP_LEARNING("DEEP LEARNING", MainCategoryEnum.DATA_SCIENCE),

    // Business
    BUSINESS("BUSINESS", MainCategoryEnum.BUSINESS),
    ENTREPRENEURSHIP("ENTREPRENEURSHIP", MainCategoryEnum.BUSINESS),
    PROJECT_MANAGEMENT("PROJECT MANAGEMENT", MainCategoryEnum.BUSINESS),
    MANAGEMENT_SKILLS("MANAGEMENT SKILLS", MainCategoryEnum.BUSINESS),

    // Finance
    INVESTING("INVESTING", MainCategoryEnum.FINANCE),
    ACCOUNTING("ACCOUNTING", MainCategoryEnum.FINANCE),
    PERSONAL_FINANCE("PERSONAL FINANCE", MainCategoryEnum.FINANCE),
    FINANCIAL_MODELING("FINANCIAL MODELING", MainCategoryEnum.FINANCE),

    // Marketing
    DIGITAL_MARKETING("DIGITAL MARKETING", MainCategoryEnum.MARKETING),
    SEO("SEO", MainCategoryEnum.MARKETING),
    SOCIAL_MEDIA_MARKETING("SOCIAL MEDIA MARKETING", MainCategoryEnum.MARKETING),
    BRAND_STRATEGY("BRAND STRATEGY", MainCategoryEnum.MARKETING),

    // Design
    GRAPHIC_DESIGN("GRAPHIC DESIGN", MainCategoryEnum.DESIGN),
    UX_UI_DESIGN("UX UI DESIGN", MainCategoryEnum.DESIGN),
    ADOBE_PHOTOSHOP("ADOBE PHOTOSHOP", MainCategoryEnum.DESIGN),
    VIDEO_EDITING("VIDEO EDITING", MainCategoryEnum.DESIGN),

    // Music
    MUSIC_PRODUCTION("MUSIC PRODUCTION", MainCategoryEnum.MUSIC),
    MUSIC_THEORY("MUSIC THEORY", MainCategoryEnum.MUSIC),
    GUITAR("GUITAR", MainCategoryEnum.MUSIC),
    PIANO("PIANO", MainCategoryEnum.MUSIC),

    // Photography
    PHOTOGRAPHY("PHOTOGRAPHY", MainCategoryEnum.PHOTOGRAPHY),
    PHOTO_EDITING("PHOTO EDITING", MainCategoryEnum.PHOTOGRAPHY),
    DSLR_TECHNIQUES("DSLR TECHNIQUES", MainCategoryEnum.PHOTOGRAPHY),

    // Teaching & Academics
    TEACHING_SKILLS("TEACHING SKILLS", MainCategoryEnum.TEACHING),
    MATH("MATH", MainCategoryEnum.TEACHING),
    SCIENCE("SCIENCE", MainCategoryEnum.TEACHING),
    HISTORY("HISTORY", MainCategoryEnum.TEACHING),

    // Language
    ENGLISH("ENGLISH", MainCategoryEnum.LANGUAGE),
    SPANISH("SPANISH", MainCategoryEnum.LANGUAGE),
    GERMAN("GERMAN", MainCategoryEnum.LANGUAGE),
    JAPANESE("JAPANESE", MainCategoryEnum.LANGUAGE),

    // Personal Development
    LEADERSHIP("LEADERSHIP", MainCategoryEnum.PERSONAL_DEVELOPMENT),
    PRODUCTIVITY("PRODUCTIVITY", MainCategoryEnum.PERSONAL_DEVELOPMENT),
    STRESS_MANAGEMENT("STRESS MANAGEMENT", MainCategoryEnum.PERSONAL_DEVELOPMENT),
    TIME_MANAGEMENT("TIME MANAGEMENT", MainCategoryEnum.PERSONAL_DEVELOPMENT),

    // Lifestyle
    TRAVEL("TRAVEL", MainCategoryEnum.LIFESTYLE),
    COOKING("COOKING", MainCategoryEnum.LIFESTYLE),
    HOME_IMPROVEMENT("HOME IMPROVEMENT", MainCategoryEnum.LIFESTYLE),

    // Health
    FITNESS("FITNESS", MainCategoryEnum.HEALTH),
    NUTRITION("NUTRITION", MainCategoryEnum.HEALTH),
    YOGA("YOGA", MainCategoryEnum.HEALTH),

    // Office Productivity
    EXCEL("EXCEL", MainCategoryEnum.OFFICE_PRODUCTIVITY),
    NOTION("NOTION", MainCategoryEnum.OFFICE_PRODUCTIVITY),
    GOOGLE_WORKSPACE("GOOGLE WORKSPACE", MainCategoryEnum.OFFICE_PRODUCTIVITY);

    private final String displayName;
    private final MainCategoryEnum mainCategory;

    CategoryEnum(String displayName, MainCategoryEnum mainCategory) {
        this.displayName = displayName;
        this.mainCategory = mainCategory;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    public MainCategoryEnum getCategoryGroup() {
        return mainCategory;
    }
}
