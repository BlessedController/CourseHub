package com.mg.course_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mg.course_service.model.Enrollment;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findAllByUserId(UUID userId);

    Boolean existsByUserIdAndCourseId(UUID userId, UUID id);

}
