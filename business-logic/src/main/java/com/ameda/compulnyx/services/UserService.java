package com.ameda.compulnyx.services;

import com.ameda.compulnyx.dtos.responses.StudentRecordsResponse;
import java.util.List;

/**
 * Author: kev.Ameda
 */
public interface UserService {

    List<StudentRecordsResponse> allUsers();
}
