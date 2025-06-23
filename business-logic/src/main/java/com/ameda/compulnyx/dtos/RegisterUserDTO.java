package com.ameda.compulnyx.dtos;

import lombok.Builder;

import java.time.LocalDate;

/**
 * Author: kev.Ameda
 */

@Builder
public class RegisterUserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String studentClass;
    private int score;
    private String photoPath;

    public RegisterUserDTO(String email,
                           String password,
                           String firstName,
                           String lastName,
                           LocalDate dob,
                           String studentClass,
                           int score,
                           String photoPath) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.studentClass = studentClass;
        this.score = score;
        this.photoPath = photoPath;
    }

    public RegisterUserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return "RegisterUserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", studentClass='" + studentClass + '\'' +
                ", score=" + score +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
