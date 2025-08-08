package com.mg.api_gateway.constant;

public class CircuitBreakerConstants {

    public static final String IDENTITY_FALLBACK = "forward:/fallback/identity";
    public static final String COURSE_FALLBACK = "forward:/fallback/course";
    public static final String MEDIA_FALLBACK = "forward:/fallback/media";

    public static final String IDENTITY_CIRCUIT_BREAKER_NAME = "identityCircuitBreaker";
    public static final String COURSE_CIRCUIT_BREAKER_NAME = "courseCircuitBreaker";
    public static final String MEDIA_CIRCUIT_BREAKER_NAME = "mediaCircuitBreaker";

}
