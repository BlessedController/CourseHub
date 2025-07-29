package com.mg.course_service.model;

import com.mg.course_service.model.enums.CategoryEnum;
import com.mg.course_service.model.enums.MainCategoryEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MainCategoryEnum mainCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnum category;


    @ManyToMany(mappedBy = "categories")
    private List<Course> courses = new ArrayList<>();

    public Category() {
    }

    public Category(MainCategoryEnum mainCategory, CategoryEnum category) {
        this.mainCategory = mainCategory;
        this.category = category;
    }

    public Category(CategoryEnum category, MainCategoryEnum mainCategory, List<Course> courses) {
        this.category = category;
        this.mainCategory = mainCategory;
        this.courses = courses;
    }


    //region Getters ve Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MainCategoryEnum getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategoryEnum mainCategory) {
        this.mainCategory = mainCategory;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    //endregion
}
