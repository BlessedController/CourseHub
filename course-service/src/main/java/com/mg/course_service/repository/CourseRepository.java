package com.mg.course_service.repository;

import com.mg.course_service.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    Optional<List<Course>> getCoursesByInstructorId(UUID instructorId);
}
