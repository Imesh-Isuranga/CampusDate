package com.campus_date.controller;

import com.campus_date.dto.LoginDTO;
import com.campus_date.dto.StudentDTO;
import com.campus_date.response.LoginResponse;
import com.campus_date.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("api/v1/student")
public class studentController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private StudentService studentService;


    @PostMapping(path = "/save")
    public String saveEmployee(@RequestBody StudentDTO studentDTO)
    {
        String id = studentService.addStudent(studentDTO);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginStudent(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse = studentService.loginStudent(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

}