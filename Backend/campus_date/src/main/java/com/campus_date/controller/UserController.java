package com.campus_date.controller;

import com.campus_date.dto.StudentDTO;
import com.campus_date.dto.ApiResponse;
import com.campus_date.dto.LoginRequest;
import com.campus_date.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles user-related HTTP requests and interactions.
 * Routes:
 * - POST /user/register: Register a new user in the system.
 * - POST /user/login: Login a user based on their email address.
 * - GET /user/all: Retrieve a list of all users in the system.
 * - GET /user/except/{userId}: Retrieve a list of all users except the user with a specific user ID.
 * - GET /user/conversation/id: Find or create a conversation ID for a pair of users based on their user IDs.
 */
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody StudentDTO studentDTO) {
        return userService.saveUser(studentDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.findUserByEmail(loginRequest.getEmail());
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findAllUsers() {
        return userService.findAllUsers();
    }


    @GetMapping("/except/{userId}")
    public ResponseEntity<ApiResponse> findAllUsersExceptThisUserId(@PathVariable int userId) {
        return userService.findAllUsersExceptThisUserId(userId);
    }


    @GetMapping("/logged/{userMail}")
    public ResponseEntity<ApiResponse> findAllUsersExceptThisUserId(@PathVariable String userMail) {
        return userService.findUserByEmail(userMail);
    }


    @GetMapping("/conversation/id")
    public ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(@RequestParam int user1Id, @RequestParam int user2Id) {
        return userService.findConversationIdByUser1IdAndUser2Id(user1Id, user2Id);
    }
}
