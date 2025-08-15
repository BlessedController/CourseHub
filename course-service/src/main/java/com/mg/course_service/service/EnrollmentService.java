package com.mg.course_service.service;

import com.mg.course_service.client.PaymentServiceClient;
import com.mg.course_service.exception.CourseAlreadyEnrolledException;
import com.mg.course_service.model.Course;
import com.mg.course_service.model.Enrollment;
import com.mg.course_service.repository.EnrollmentRepository;
import com.mg.course_service.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentService.class);
    private final EnrollmentRepository enrollmentRepository;
    private final JwtUtil jwtUtil;
    private final PaymentServiceClient paymentServiceClient;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             JwtUtil jwtUtil,
                             PaymentServiceClient paymentServiceClient) {
        this.enrollmentRepository = enrollmentRepository;
        this.jwtUtil = jwtUtil;
        this.paymentServiceClient = paymentServiceClient;
    }

    //TODO: IMPROVE IT. WHAT IS SAGA DP?!
    @Transactional
    public void enrollCourse(Course course, String token) {

        UUID userId = jwtUtil.getUserIdFromToken(token);

        boolean alreadyEnrolled = enrollmentRepository
                .existsByUserIdAndCourseId(userId, course.getId());

        if (alreadyEnrolled) {
            log.info("User {} already enrolled in course {}", userId, course.getId());
            throw new CourseAlreadyEnrolledException("You already enrolled the course by name: " + course.getTitle());
        }

        Boolean isPaid = paymentServiceClient.pay().getBody();

        if (Boolean.TRUE.equals(isPaid)) {

            Enrollment enrollment = new Enrollment.Builder()
                    .course(course)
                    .userId(userId)
                    .build();

            enrollmentRepository.save(enrollment);
            log.info("User {} enrolled in course {}", userId, course.getId());
        } else {
            log.info("Payment not successful, enrollment skipped for user token {}", token);
        }
    }


}
