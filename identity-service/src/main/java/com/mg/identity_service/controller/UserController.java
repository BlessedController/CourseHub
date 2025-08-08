package com.mg.identity_service.controller;

import com.mg.identity_service.dto.requests.LoginRequest;
import com.mg.identity_service.dto.requests.*;
import com.mg.identity_service.dto.responses.UserResponse;
import com.mg.identity_service.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.mg.identity_service.constant.UserApiUrls.*;

@RestController
@RequestMapping(USER_API_BASE_URL)
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(REGISTER_URL)
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(LOGIN_URL)
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userService.authenticate(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping(ADMIN_ALL_USERS_URL)
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(GET_BY_ID_URL)
    public ResponseEntity<UserResponse> getUserById(@NotNull @PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping(GET_SELF_URL)
    public ResponseEntity<UserResponse> getSelfByToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getSelfByToken(token));
    }

    @DeleteMapping(DELETE_BY_ID_URL)
    public ResponseEntity<Void> deleteUserById(@NotNull @PathVariable UUID id,
                                               @RequestHeader("Authorization") String token) {
        userService.deleteUserById(id, token);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(UPDATE_USER_URL)
    public ResponseEntity<Void> updateUser(@NotNull @PathVariable UUID id,
                                           @Valid @RequestBody UpdateUserInfoRequest request,
                                           @RequestHeader("Authorization") String token) {
        userService.updateUser(id, request, token);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(UPDATE_USERNAME_URL)
    public ResponseEntity<Void> updateUserUsername(@NotNull @PathVariable UUID id,
                                                   @Valid @RequestBody UpdateUserUsernameRequest request,
                                                   @RequestHeader("Authorization") String token) {
        userService.updateUsername(id, request, token);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(UPDATE_PASSWORD_URL)
    public ResponseEntity<Void> updateUserPassword(@NotNull @PathVariable UUID id,
                                                   @Valid @RequestBody UpdateUserPasswordRequest request,
                                                   @RequestHeader("Authorization") String token) {
        userService.updateUserPassword(id, request, token);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(UPDATE_EMAIL_URL)
    public ResponseEntity<Void> updateUserEmail(@NotNull @PathVariable UUID id,
                                                @Valid @RequestBody UpdateUserEmailRequest request,
                                                @RequestHeader("Authorization") String token) {
        userService.updateUserEmail(id, request, token);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(UPDATE_PHONE_NUMBER_URL)
    public ResponseEntity<Void> updateUserPhoneNumber(@NotNull @PathVariable UUID id,
                                                      @Valid @RequestBody UpdateUserPhoneNumberRequest request,
                                                      @RequestHeader("Authorization") String token) {
        userService.updateUserPhoneNumber(id, request, token);
        return ResponseEntity.noContent().build();
    }
}

