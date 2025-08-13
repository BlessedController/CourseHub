package com.mg.course_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "COURSES")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 150)
    private String title;

    @Column(name = "ABOUT", nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private UUID instructorId;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean isPublished = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Category> categories = new HashSet<>();

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    private List<Video> courseVideos = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    private List<Enrollment> enrollments = new ArrayList<>();


    public Course() {
    }

    //region Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(UUID instructorId) {
        this.instructorId = instructorId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<Video> getCourseVideos() {
        return courseVideos;
    }

    public void setCourseVideos(List<Video> courseVideos) {
        this.courseVideos = courseVideos;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    //endregion

    public static class Builder {
        private String title;
        private String description;
        private UUID instructorId;
        private Double price;
        private Boolean isPublished = false;
        private Set<Category> categories = new HashSet<>();

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder instructorId(UUID instructorId) {
            this.instructorId = instructorId;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder isPublished(Boolean isPublished) {
            this.isPublished = isPublished;
            return this;
        }

        public Builder categories(Set<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Course build() {
            Course course = new Course();
            course.setTitle(this.title);
            course.setDescription(this.description);
            course.setInstructorId(this.instructorId);
            course.setPrice(this.price);
            course.setPublished(this.isPublished);
            course.setCategories(this.categories);
            return course;
        }
    }
}