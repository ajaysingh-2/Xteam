package com.xsworld.userservice.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            message = "Password must have at least 1 uppercase letter, 1 lowercase letter, 1 special character, 1 number, and be at least 8 characters long")
    private String password;

    @NotNull(message = "Username cannot be null")
    private String username;

    private String role;

    @Pattern(regexp = "\\d{10}", message = "Mobile number must be a 10-digit number")
    private String mobile;

    @NotNull(message = "Date of Birth cannot be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    private LocalDateTime createdAt;

}
