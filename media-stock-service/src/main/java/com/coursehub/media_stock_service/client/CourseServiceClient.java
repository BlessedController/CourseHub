package com.coursehub.media_stock_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "course-service", path = "/v1/course")
public interface CourseServiceClient {


    @GetMapping("/validate-owner")
    ResponseEntity<Boolean> isUserOwnerOfCourse(@RequestParam UUID courseId,
                                                @RequestHeader("Authorization") String token);

}
