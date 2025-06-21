package com.ameda.compulnyx.services;

import com.ameda.compulnyx.dtos.LoginUserDTO;
import com.ameda.compulnyx.dtos.RegisterUserDTO;
import com.ameda.compulnyx.entities.User;
import org.springframework.stereotype.Service;

/**
 * Author: kev.Ameda
 */

@Service
public interface AuthenticationService {

    public User signUp(RegisterUserDTO registerUserDTO);
    public User authenticate(LoginUserDTO loginUserDTO);
}
