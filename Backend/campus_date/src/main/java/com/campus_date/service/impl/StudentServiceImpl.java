package com.campus_date.service.impl;

import com.campus_date.dao.StudentRepo;
import com.campus_date.dto.LoginDTO;
import com.campus_date.dto.StudentDTO;
import com.campus_date.entity.Student;
import com.campus_date.response.LoginResponse;
import com.campus_date.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepo studentRepo;
    @Override
    public String addStudent(StudentDTO studentDTO) {
        Student student = new Student(
                studentDTO.getStudentId(),
                studentDTO.getStudentName(),
                studentDTO.getEmail(),
                studentDTO.getUniversity(),
                studentDTO.getFaculty(),
                studentDTO.getAddress(),
                studentDTO.getDate_of_birth(),
                studentDTO.getInterest_gender(),
                studentDTO.getInterest_age_limit(),
                studentDTO.getInterest_distric(),
                studentDTO.getImages(),
                this.passwordEncoder.encode(studentDTO.getPassword())
        );

        studentRepo.save(student);
        return student.getStudentName();
    }

    @Override
    public LoginResponse  loginStudent(LoginDTO loginDTO) {
        String msg = "";
        Student student1 = studentRepo.findByEmail(loginDTO.getEmail());
        if (student1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = student1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Student> employee = studentRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("password Not Match", false);
            }
        }else {
            return new LoginResponse("Email not exits", false);
        }
    }

}
