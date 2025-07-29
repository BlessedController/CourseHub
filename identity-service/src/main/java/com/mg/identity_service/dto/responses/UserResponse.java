package com.mg.identity_service.dto.responses;

import com.mg.identity_service.model.Gender;
import com.mg.identity_service.model.Role;
import com.mg.identity_service.model.User;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String phoneNumber,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String overview,
        String description,
        Gender gender,
        Role role
) {
    public static UserResponse convert(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getOverview(),
                user.getdescription(),
                user.getGender(),
                user.getRole()
        );

    }
}
