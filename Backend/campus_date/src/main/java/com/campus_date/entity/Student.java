package com.campus_date.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name="student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Column(name="student_id", length = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @Column(name="student_name", length = 255)
    private String studentName;

    @Column(name="email", length = 255)
    private String email;

    @Column(name="university", length = 255)
    private String university;

    @Column(name="faculty", length = 255)
    private String faculty;

    @Column(name="address", length = 255)
    private String address;

    @Column(name="date_of_birth", length = 255)
    private String date_of_birth;

    @Column(name="interest_gender", length = 255)
    private String interest_gender;

    @Column(name="interest_age_limit", length = 255)
    private String interest_age_limit;

    @Column(name="interest_distric", length = 255)
    private String interest_distric;

    @ElementCollection
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @Column(name="password", length = 255)
    private String password;
}