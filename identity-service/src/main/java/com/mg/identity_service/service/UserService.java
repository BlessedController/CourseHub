package com.mg.identity_service.service;

import com.mg.identity_service.dto.requests.LoginRequest;
import com.mg.identity_service.dto.requests.*;
import com.mg.identity_service.dto.responses.ProfilePhotoResponse;
import com.mg.identity_service.dto.responses.UserResponse;
import com.mg.identity_service.exception.DuplicateFieldException;
import com.mg.identity_service.exception.SelfOrAdminOnlyException;
import com.mg.identity_service.exception.UserNotFoundException;
import com.mg.identity_service.model.Role;
import com.mg.identity_service.model.User;
import com.mg.identity_service.repository.UserRepository;
import com.mg.identity_service.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.mg.identity_service.config.RabbitMQConfig.*;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationProvider authenticationProvider,
                       JwtService jwtService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }

    protected User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by Id: " + id));
    }

    protected void authorizeSelfOrAdmin(UUID targetUserId, String token) {
        UUID currentUserId = jwtUtil.getUserIdFromToken(token);
        if (!jwtUtil.isAdmin(token) && !Objects.equals(currentUserId, targetUserId)) {
            throw new SelfOrAdminOnlyException("Only the user or an admin can perform this action.");
        }

    }

    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateFieldException("Username is already taken.");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateFieldException("Email is already registered.");
        }

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateFieldException("PhoneNumber is already exist.");
        }

        User user = new User.Builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .gender(request.gender())
                .overview(request.overview())
                .description(request.description())
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    public void updateUser(UUID targetUserId, UpdateUserInfoRequest request, String token) {
        authorizeSelfOrAdmin(targetUserId, token);
        User user = findById(targetUserId);

        if (request.firstName() != null) user.setFirstName(request.firstName());
        if (request.lastName() != null) user.setLastName(request.lastName());
        if (request.birthDate() != null) user.setBirthDate(request.birthDate());
        if (request.gender() != null) user.setGender(request.gender());

        userRepository.save(user);
    }

    public void updateUsername(UUID targetUserId, UpdateUserUsernameRequest request, String token) {
        authorizeSelfOrAdmin(targetUserId, token);

        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateFieldException("Username is already taken.");
        }

        User user = findById(targetUserId);

        user.setUsername(request.username());
        userRepository.save(user);
    }

    public void updateUserPassword(UUID targetUserId, UpdateUserPasswordRequest request, String token) {
        authorizeSelfOrAdmin(targetUserId, token);

        User user = findById(targetUserId);

        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }

    public void updateUserEmail(UUID targetUserId, UpdateUserEmailRequest request, String token) {
        authorizeSelfOrAdmin(targetUserId, token);

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateFieldException("Email is already registered.");
        }

        User user = findById(targetUserId);

        user.setEmail(request.email());
        userRepository.save(user);
    }

    public void updateUserPhoneNumber(UUID targetUserId, UpdateUserPhoneNumberRequest request, String token) {
        authorizeSelfOrAdmin(targetUserId, token);

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateFieldException("PhoneNumber is already exist.");
        }

        User user = findById(targetUserId);

        user.setPhoneNumber(request.phoneNumber());
        userRepository.save(user);
    }

    @Transactional
    @RabbitListener(queues = UPDATE_ROLE_QUEUE)
    public void changeRoleToAuthor(UUID userId) {
        try {
            User user = findById(userId);
            user.setRole(Role.ROLE_AUTHOR);
            userRepository.save(user);
        } catch (UserNotFoundException exception) {
            logger.error(exception.getMessage());
        } catch (Exception exception) {
            logger.error("Unexpected error", exception);
        }
    }

    public void deleteUserById(UUID targetUserId, String token) {
        authorizeSelfOrAdmin(targetUserId, token);
        userRepository.deleteById(targetUserId);
    }

    public UserResponse getUserById(UUID targetUserId) {
        User user = findById(targetUserId);

        return UserResponse.convertToDTO(user);
    }

    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map(UserResponse::convertToDTO)
                .toList();
    }

    //TODO: DO SOMETHING IF OR TRY CATCH
    public String authenticate(LoginRequest request) {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(request.username(), request.password());

        authenticationProvider.authenticate(auth);

        return jwtService.generateToken(request);
    }

    public UserResponse getSelfByToken(String token) {
        UUID userId = jwtUtil.getUserIdFromToken(token);
        User user = findById(userId);
        return UserResponse.convertToDTO(user);
    }

    @Transactional
    @RabbitListener(queues = ADD_PHOTO_QUEUE)
    public void receiveProfilePhotoNameFromMediaStock(ProfilePhotoResponse response) {
        UUID userId = jwtUtil.getUserIdFromToken(response.token());
        User user = findById(userId);
        user.setProfilePhotoName(response.profilePhotoName());
        userRepository.save(user);
    }


    @Transactional
    @RabbitListener(queues = DELETE_PHOTO_QUEUE)
    public void deleteProfilePhotoName(String token) {
        logger.info("Received token {}", token);
        UUID userId = jwtUtil.getUserIdFromToken(token);
        User user = findById(userId);
        user.setProfilePhotoName(null);
        userRepository.save(user);
    }


}
