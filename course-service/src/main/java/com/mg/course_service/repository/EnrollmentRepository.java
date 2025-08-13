package com.mg.course_service.repository;

import com.mg.course_service.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findAllByUserId(UUID userId);
}
