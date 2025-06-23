package com.ameda.compulnyx.config;

import com.ameda.compulnyx.entities.Student;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;

/**
 * Author: kev.Ameda
 */

public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(new User("test1@gmail.com", passwordEncoder.encode("test1"), "test1@gmail.com",
                    Student.builder()
                            .firstName("Test1")
                            .lastName("User1")
                            .dob(LocalDate.of(2000, Month.APRIL,20))
                            .build()));

            userRepository.save(new User("test2@gmail.com", passwordEncoder.encode("test2"), "test2@gmail.com",
                    Student.builder()
                            .firstName("Test2")
                            .lastName("User2")
                            .dob(LocalDate.of(2001, Month.DECEMBER,12))
                            .build()));

            userRepository.save(new User("test3@gmail.com", passwordEncoder.encode("test3"), "test3@gmail.com",
                    Student.builder()
                            .firstName("Test3")
                            .lastName("User3")
                            .dob(LocalDate.of(2004, Month.JULY,10))
                            .build()));
        }
    }

}
