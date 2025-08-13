package com.mg.course_service.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ENROLLMENTS")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(nullable = false)
    private UUID userId;

    public Enrollment() {
    }

    //region Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    //endregion

    public static class Builder {
        private Course course;

        private UUID userId;

        public Builder course(Course course) {
            this.course = course;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Enrollment build() {
            Enrollment enrollment = new Enrollment();
            enrollment.setCourse(course);
            enrollment.setUserId(userId);
            return enrollment;
        }
    }
}
