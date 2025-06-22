package com.ameda.compulnyx.entities;

import com.ameda.compulnyx.utility.annotation.DOBRange;
import jakarta.persistence.Column;
import java.time.LocalDate;

/**
 * Author: kev.Ameda
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;

@Embeddable
@Check(constraints = "score >= 55 AND score <= 85")
public class Student {

    @Column(nullable = false, length = 8)
    @Size(min = 3, max = 8, message = "First name must be between 3 and 8 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabet characters")
    private String firstName;

    @Column(nullable = false, length = 8)
    @Size(min = 3, max = 8, message = "First name must be between 3 and 8 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabet characters")
    private String lastName;

    @Column(nullable = false)
    @DOBRange
    private LocalDate dob;

    @Column(name = "class_name", nullable = false)
    @Pattern(
            regexp = "^(Class1|Class2|Class3|Class4|Class5)$",
            message = "Class must be one of: Class1, Class2, Class3, Class4, Class5"
    )
    private String studentClass;

    @Column(nullable = false)
    @Min(55)
    @Max(85)
    private int score;

    @Column(nullable = false)
    private int status = 1; // designates the status to be active

    @Column(nullable = false)
    private String photoPath = "";

    public Student() {}

    public Student(String firstName, String lastName, LocalDate dob,
                   String studentClass, int score, int status, String photoPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.studentClass = studentClass;
        this.score = score;
        this.status = status;
        this.photoPath = photoPath;
    }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getClassName() { return studentClass; }
    public void setClassName(String studentClass) { this.studentClass = studentClass; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    @Override
    public String toString() {
        return "Student{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", studentClass='" + studentClass + '\'' +
                ", score=" + score +
                ", status=" + status +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
