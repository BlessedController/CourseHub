package com.mg.identity_service.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "USERS")
public class User implements UserDetails {

    //region fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 30, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 13, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column()
    //   @MinimumAge(value = 13, message = "Kullanıcı en az 13 yaşında olmalıdır")
    private LocalDate birthDate;

    @Column(length = 70)
    private String overview;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column()
    String profilePhotoName;

    //endregion

    //region Constructors

    public User() {
    }

    public User(String username,
                String password,
                String phoneNumber,
                String email,
                String firstName,
                String lastName,
                Gender gender,
                Role role
    ) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.role = role;
    }

    public User(String username,
                String password,
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
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.overview = overview;
        this.description = description;
        this.gender = gender;
        this.role = role;
    }


    //endregion

    //region Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getdescription() {
        return description;
    }

    public void setDetailedDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfilePhotoName() {
        return profilePhotoName;
    }

    public void setProfilePhotoName(String profilePhotoName) {
        this.profilePhotoName = profilePhotoName;
    }

    //endregion

    //region UserDetails Overrides

    //TODO: DO ACTUALLY OVERRIDE

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    //endregion

    @Override
    public String toString() {
        return "User{" + "\n" +
                "  id=" + id + ",\n" +
                "  username='" + username + '\'' + ",\n" +
                "  password='" + password + '\'' + ",\n" +
                "  phoneNumber='" + phoneNumber + '\'' + ",\n" +
                "  email='" + email + '\'' + ",\n" +
                "  firstName='" + firstName + '\'' + ",\n" +
                "  lastName='" + lastName + '\'' + ",\n" +
                "  birthDate=" + birthDate + ",\n" +
                "  summary=" + overview + ",\n" +
                "  description=" + description + ",\n" +
                "  gender=" + gender + ",\n" +
                "  role=" + role + "\n" +
                '}';
    }

}