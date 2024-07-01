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
    private String first_Name;
    private String last_Name;
    private String email;
    private String university;
    private String faculty;
    private String address;
    private String date_of_birth;
    private String gender;
    private String password;
}
