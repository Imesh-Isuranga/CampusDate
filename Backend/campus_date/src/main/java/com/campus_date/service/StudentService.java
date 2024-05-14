package com.campus_date.service;

import com.campus_date.dto.LoginDTO;
import com.campus_date.dto.StudentDTO;
import com.campus_date.response.LoginResponse;

import java.util.List;

public interface StudentService {
    LoginResponse addStudent(StudentDTO studentDTO);

    LoginResponse loginStudent(LoginDTO loginDTO);

}
