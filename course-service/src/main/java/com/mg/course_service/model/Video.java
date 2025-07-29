package com.mg.course_service.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "VIDEOS")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String filename;

    @Column
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Video() {
    }

    public Video(String filename, String displayName, Course course) {
        this.filename = filename;
        this.displayName = displayName;
        this.course = course;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", displayName='" + displayName + '\'' +
                ", course=" + course +
                '}';
    }
}
