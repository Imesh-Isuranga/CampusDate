package com.campus_date.service;

import com.campus_date.dto.LoginDTO;
import com.campus_date.dto.StudentDTO;
import com.campus_date.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

/**
 * An interface for user-related operations and services.
 */
public interface UserService {

    ResponseEntity<ApiResponse> saveUser(StudentDTO studentDTO);

    ResponseEntity<ApiResponse> findUserByEmail(LoginDTO loginDTO);

    ResponseEntity<ApiResponse> findAllUsers();

    ResponseEntity<ApiResponse> findAllUsersExceptThisUserId(int userId);

    ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(int user1Id, int user2Id);

    ResponseEntity<ApiResponse> findUsersByUserMail(String userMail);
}
