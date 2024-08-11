package com.compulnyx.project.test_excercise.security.auth;
/*
*
@author ameda
@project Books
*
*/
import com.compulnyx.project.test_excercise.security.auth.DTO.AuthRequest;
import com.compulnyx.project.test_excercise.security.auth.DTO.AuthResponse;
import com.compulnyx.project.test_excercise.security.auth.DTO.RoleRequest;
import com.compulnyx.project.test_excercise.security.auth.DTO.SignUpRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("auth")
@Tag(name="Authentication")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request) throws MessagingException {
        authenticationService.signUp(request);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/add/role")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request){
        authenticationService.createRole(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws MessagingException {
        authenticationService.activateAccount(token);
    }
}
