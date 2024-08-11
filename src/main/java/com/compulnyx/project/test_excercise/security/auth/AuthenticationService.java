package com.compulnyx.project.test_excercise.security.auth;
/*
*
@author ameda
@project Books
*
*/
import com.compulnyx.project.test_excercise.Domains.role.Role;
import com.compulnyx.project.test_excercise.Domains.role.RoleRepository;
import com.compulnyx.project.test_excercise.Domains.customer.entity.Customer;
import com.compulnyx.project.test_excercise.Domains.customer.entity.Token;
import com.compulnyx.project.test_excercise.Domains.customer.repo.CustomerRepository;
import com.compulnyx.project.test_excercise.Domains.customer.repo.TokenRepository;
import com.compulnyx.project.test_excercise.common.email.EmailService;
import com.compulnyx.project.test_excercise.common.email.EmailTemplateName;
import com.compulnyx.project.test_excercise.security.JwtService;
import com.compulnyx.project.test_excercise.security.auth.DTO.AuthRequest;
import com.compulnyx.project.test_excercise.security.auth.DTO.AuthResponse;
import com.compulnyx.project.test_excercise.security.auth.DTO.RoleRequest;
import com.compulnyx.project.test_excercise.security.auth.DTO.SignUpRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void signUp(SignUpRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(()->new IllegalStateException("role customer not initialized."));
        var customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        customerRepository.save(customer);
        sendValidationEmail(customer);
    }

    private void sendValidationEmail(Customer customer) throws MessagingException {
        var newToken = generateAndSaveActivationToken(customer);
        emailService.sendEmail(
                customer.getEmail(),
                customer.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String  generateAndSaveActivationToken(Customer customer) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusSeconds(40))
                .customer(customer)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i=0; i <length;++i){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return  codeBuilder.toString();
    }

    public void createRole(RoleRequest request) {
        Role role = Role.builder()
                .name(request.getRoleName())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        roleRepository.save(role);
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getCustomer());
            log.info("Activation token has expired. A new token has been sent to your address");
            throw new RuntimeException("Activation token has expired. A new token has been sent to your address");
        }

        var user = customerRepository.findById(savedToken.getCustomer().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        customerRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    public AuthResponse authenticate(AuthRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var customer = ((Customer) auth.getPrincipal());
        claims.put("fullName", customer.fullName());

        var jwtToken = jwtService.generateToken(claims, (Customer) auth.getPrincipal());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
