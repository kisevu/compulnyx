package com.compulnyx.project.test_excercise.security.auth.DTO;/*
*
@author ameda
@project Books
*
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpRequest {
    @NotEmpty(message = "Firstname is a required field")
    @NotNull(message = "Firstname is a required field")
    private String firstName;
    @NotEmpty(message = "Lastname is a required field")
    @NotNull(message = "Lastname is a required field")
    private String lastName;
    @Email(message = "make sure your email is well formatted")
    @NotEmpty(message = "Email is a required field")
    @NotNull(message = "Email is a required field")
    private String email;
    @NotEmpty(message = "Password is a required field")
    @NotNull(message = "Password is a required field")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
}
