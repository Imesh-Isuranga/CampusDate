package com.campus_date.dao;

import com.campus_date.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Integer> {
    Optional<Student> findOneByEmailAndPassword(String email, String password);
    Student findByEmail(String email);
}
