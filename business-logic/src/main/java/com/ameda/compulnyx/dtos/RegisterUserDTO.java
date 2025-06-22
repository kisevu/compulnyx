package com.ameda.compulnyx.dtos;

import com.ameda.compulnyx.entities.Student;

/**
 * Author: kev.Ameda
 */

public class RegisterUserDTO {

    /*
     *  firstName, lasttName, dob(LocalDate), studentClass(class1 String),
     *  score(int), photopath (string)
     *
     * */
    private String email;
    private String password;
    private String username;
    private Student student;

    public RegisterUserDTO(){
    }

    public RegisterUserDTO(String email, String password, String username,Student student) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.student = student;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterUserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", student=" + student +
                '}';
    }
}
