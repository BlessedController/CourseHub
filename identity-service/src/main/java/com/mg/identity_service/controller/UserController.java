package com.mg.identity_service.controller;

import com.mg.identity_service.dto.requests.LoginRequest;
import com.mg.identity_service.dto.requests.*;
import com.mg.identity_service.dto.responses.UserResponse;
import com.mg.identity_service.service.UserService;
import jakarta.servlet.http.HttpServletResponse; // Eğer cookie kullanmayacaksanız kaldırabilirsiniz
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = userService.authenticate(request);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/admin/all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@NotNull @PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/self")
    public ResponseEntity<UserResponse> getSelfByToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getSelfByToken(token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@NotNull @PathVariable UUID id,
                                               @RequestHeader("Authorization") String token) {
        userService.deleteUserById(id, token);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-user/{id}")
    public ResponseEntity<Void> updateUser(@NotNull @PathVariable UUID id,
                                           @Valid @RequestBody UpdateUserInfoRequest request,
                                           @RequestHeader("Authorization") String token) {
        userService.updateUser(id, request, token);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-username/{id}")
    public ResponseEntity<Void> updateUserUsername(@NotNull @PathVariable UUID id,
                                                   @Valid @RequestBody UpdateUserUsernameRequest request,
                                                   @RequestHeader("Authorization") String token) {
        userService.updateUsername(id, request, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PatchMapping("/update-password/{id}")
    public ResponseEntity<Void> updateUserPassword(@NotNull @PathVariable UUID id,
                                                   @Valid @RequestBody UpdateUserPasswordRequest request,
                                                   @RequestHeader("Authorization") String token) {
        userService.updateUserPassword(id, request, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PatchMapping("/update-email/{id}")
    public ResponseEntity<Void> updateUserEmail(@NotNull @PathVariable UUID id,
                                                @Valid @RequestBody UpdateUserEmailRequest request,
                                                @RequestHeader("Authorization") String token) {
        userService.updateUserEmail(id, request, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PatchMapping("/update-phone-number/{id}")
    public ResponseEntity<Void> updateUserPhoneNumber(@NotNull @PathVariable UUID id,
                                                      @Valid @RequestBody UpdateUserPhoneNumberRequest request,
                                                      @RequestHeader("Authorization") String token) {
        userService.updateUserPhoneNumber(id, request, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}