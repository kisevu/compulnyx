package com.ameda.compulnyx.config;
import com.ameda.compulnyx.entities.Student;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


/**
 * Author: kev.Ameda
 */

//@Component
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void insertTestUsers() {
        insertUserIfNotExists("alice@example.com", "alice.johnson", "Alice", "Johnson", LocalDate.of(2005, 4, 12), "Class1");
//        insertUserIfNotExists("bob@example.com", "bob.smith", "Bob", "Smith", LocalDate.of(2007, 9, 23), "Class2");
    }

    private void insertUserIfNotExists(String email, String username, String firstName, String lastName, LocalDate dob, String className) {
            log.info("Seeding user: {}", email);
            Student student = new Student(firstName, lastName, dob, className, 75, 1, "");

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(getPassword("password123"));
            user.setEnabled(true);
            user.setStudent(student);
//            userRepository.save(user);
            log.info("✅ User inserted: {}", email);
    }



    private String getPassword( String value ){
       String result = passwordEncoder.encode(value);
       log.info("Password: {}",result);
       return result;
   }
}
