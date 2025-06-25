package com.ameda.compulnyx.config;
import com.ameda.compulnyx.entities.Student;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


/**
 * Author: kev.Ameda
 */

@Component
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void insertTestUsers() {
        if (userRepository.count() == 0) {
            // Student 1
            Student student1 = new Student(
                    "Alice", "Johnson",
                    LocalDate.of(2005, 4, 12),
                    "Class1", 75, 1, ""
            );

            User user1 = new User();
            user1.setUsername("alice.johnson");
            user1.setEmail("alice@example.com");
            user1.setPassword(passwordEncoder.encode("password123"));
            user1.setEnabled(true);
            user1.setStudent(student1);

            // Student 2
            Student student2 = new Student(
                    "Bob", "Smith",
                    LocalDate.of(2007, 9, 23),
                    "Class2", 68, 1, ""
            );

            User user2 = new User();
            user2.setUsername("bob.smith");
            user2.setEmail("bob@example.com");
            user2.setPassword(passwordEncoder.encode("password123"));
            user2.setEnabled(true);
            user2.setStudent(student2);

            // Save users
            userRepository.save(user1);
            userRepository.save(user2);

            System.out.println("✅ Test users inserted successfully.");
        }
    }
}
