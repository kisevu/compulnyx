package com.compulnyx.project.test_excercise.security.auth.DTO;/*
*
@author ameda
@project Books
*
*/

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String token;
}
