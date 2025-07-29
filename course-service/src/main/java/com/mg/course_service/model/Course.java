package com.mg.course_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "courses")
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
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Video> courseVideos = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    //region Constructors

    public Course() {
    }

    public Course(String title,
                  String description,
                  UUID instructorId,
                  Double price,
                  Boolean isPublished,
                  Set<Category> categories) {
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.price = price;
        this.isPublished = isPublished;
        this.categories = categories;
    }


    //endregion

    //region Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public List<Video> getCourseVideos() {
        return courseVideos;
    }

    public void setCourseVideos(List<Video> courseVideos) {
        this.courseVideos = courseVideos;
    }

//endregion

}
