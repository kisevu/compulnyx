package com.ameda.compulnyx.services.impl;

import com.ameda.compulnyx.dtos.LoginUserDTO;
import com.ameda.compulnyx.dtos.RegisterUserDTO;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.repository.UserRepository;
import com.ameda.compulnyx.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

/**
 * Author: kev.Ameda
 */

@Service
public class AuthenticationImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationImpl(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User signUp(RegisterUserDTO registerUserDTO) {
        User user = new User(registerUserDTO.getUsername(),
                passwordEncoder.encode(registerUserDTO.getPassword()),
                registerUserDTO.getEmail());
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public User authenticate(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(Objects.nonNull(user)){
            user.setEnabled(true);
            userRepository.save(user);
            if (!user.isEnabled()) {
                throw new RuntimeException("Account not verified. Please verify your account.");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserDTO.getEmail(),
                            loginUserDTO.getPassword()
                    )
            );
        }
        return user;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
