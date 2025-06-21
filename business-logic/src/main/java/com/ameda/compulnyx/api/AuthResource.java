package com.ameda.compulnyx.api;

import com.ameda.compulnyx.dtos.LoginUserDTO;
import com.ameda.compulnyx.dtos.RegisterUserDTO;
import com.ameda.compulnyx.dtos.responses.LoginResponse;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.services.AuthenticationService;
import com.ameda.compulnyx.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: kev.Ameda
 */

@RestController
@RequestMapping("/auth")
public class AuthResource {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthResource(JwtService jwtService,
                        AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
        User registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto){
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
