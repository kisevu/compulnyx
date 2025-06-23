package com.ameda.compulnyx.api;

import com.ameda.compulnyx.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: kev.Ameda
 */

@RestController
@RequestMapping("/students")
@SecurityRequirement(name = "bearerAuth")
public class StudentsResource {

    private static Logger log = LoggerFactory.getLogger(StudentsResource.class);
    private final UserService userService;

    public StudentsResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-students")
    public ResponseEntity<?> getAllStudents(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.allUsers());
    }

}
