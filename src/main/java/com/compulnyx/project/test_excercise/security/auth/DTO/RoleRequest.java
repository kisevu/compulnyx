package com.compulnyx.project.test_excercise.security.auth.DTO;
/*
*
@author ameda
@project test-excercise
*
*/

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    private String roleName;
}
