package com.ameda.compulnyx.services.impl;

import com.ameda.compulnyx.dtos.responses.StudentRecordsResponse;
import com.ameda.compulnyx.entities.User;
import com.ameda.compulnyx.repository.UserRepository;
import com.ameda.compulnyx.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: kev.Ameda
 */

@Service
@Transactional
public class UserServiceImpl  implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<StudentRecordsResponse> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users.stream()
                .map(this::mapToStudentRecordsResponse)
                .toList();
    }

    private StudentRecordsResponse mapToStudentRecordsResponse(User user){
        return StudentRecordsResponse.builder()
                .studentId(user.getStudentId())
                .firstName(user.getStudent().getFirstName())
                .lastName(user.getStudent().getLastName())
                .dob(user.getStudent().getDob())
                .className(user.getStudent().getClassName())
                .score(user.getStudent().getScore())
                .status(user.getStudent().getStatus())
                .photoPath(user.getStudent().getPhotoPath())
                .build();
    }


}
