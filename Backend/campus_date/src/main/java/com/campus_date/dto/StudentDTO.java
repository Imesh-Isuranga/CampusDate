package com.campus_date.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private int studentId;
    private String studentName;
    private String email;
    private String university;
    private String faculty;
    private String address;
    private String date_of_birth;
    private String gender;
    private String interest_gender;
    private String interest_age_limit;
    private String interest_distric;
    private List<String> images;
    private String password;
}
