package com.ameda.compulnyx.services.impl;

import com.ameda.compulnyx.dtos.LoginUserDTO;
import com.ameda.compulnyx.dtos.RegisterUserDTO;
import com.ameda.compulnyx.entities.Student;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.repository.UserRepository;
import com.ameda.compulnyx.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        RegisterUserDTO req = RegisterUserDTO.builder()
                .email(registerUserDTO.getEmail())
                .password(registerUserDTO.getPassword())
                .firstName(registerUserDTO.getFirstName())
                .lastName(registerUserDTO.getLastName())
                .dob(registerUserDTO.getDob())
                .studentClass(registerUserDTO.getStudentClass())
                .score(registerUserDTO.getScore())
                .photoPath(registerUserDTO.getPhotoPath())
                .build();
        Student student = Student.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .dob(req.getDob())
                .studentClass(req.getStudentClass())
                .score(req.getScore())
                .photoPath(req.getPhotoPath())
                .status(1)
                .build();
        User user = new User(req.getEmail(),
                passwordEncoder.encode(req.getPassword()),
                req.getEmail(),student);
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
                throw new RuntimeException("Account not enabled. Please contact Admin for Assistance.");
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
