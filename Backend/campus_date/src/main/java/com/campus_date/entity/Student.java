package com.campus_date.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @Column(name="student_id", length = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name="university", length = 255)
    private String university;

    @Column(name="faculty", length = 255)
    private String faculty;

    @Column(name="address", length = 255)
    private String address;

    @Column(name="date_of_birth", length = 255)
    private String date_of_birth;

    @Column(name="gender", length = 10)
    private String gender;

    @Column(name="password", length = 255)
    private String password;
}
