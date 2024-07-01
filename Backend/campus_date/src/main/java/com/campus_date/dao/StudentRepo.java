package com.campus_date.dao;

import com.campus_date.entity.Student;
import com.campus_date.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Integer> {

    @Query("SELECT u FROM Student u WHERE u.user <> ?1")
    List<Student> findAllUsersExceptThisUserId(int userId);

    @Query("SELECT s FROM Student s WHERE s.user.userId = ?1")
    Student findByUserId(int userId);
}
