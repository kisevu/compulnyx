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
public class AuthRequest {
    @Email(message = "Provided the correct email format")
    @NotEmpty(message = "Email should be provided")
    @NotNull(message = "Provide email")
    private String email;

    @NotEmpty(message = "Password should be provided")
    @NotNull(message = "Provide email")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
}
