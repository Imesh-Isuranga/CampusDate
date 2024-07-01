package com.campus_date.service.impl;

import com.campus_date.dao.StudentRepo;
import com.campus_date.dao.ConversationRepository;
import com.campus_date.dao.UserRepository;
import com.campus_date.dto.LoginDTO;
import com.campus_date.dto.StudentDTO;
import com.campus_date.dto.ApiResponse;
import com.campus_date.entity.Student;
import com.campus_date.entity.Conversation;
import com.campus_date.entity.User;
import com.campus_date.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserService interface that provides user-related services.
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StudentRepo studentRepo;
    private final ConversationRepository conversationRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<ApiResponse> saveUser(StudentDTO studentDTO) {
        try {
            User user1 = new User(studentDTO.getFirst_Name(),studentDTO.getLast_Name(),studentDTO.getEmail());
            User user = new User(user1.getUserId(),studentDTO.getFirst_Name(),studentDTO.getLast_Name(),studentDTO.getEmail());
            user = userRepository.save(user);
            Student student = new Student(
                    studentDTO.getStudentId(),
                    user,
                    studentDTO.getUniversity(),
                    studentDTO.getFaculty(),
                    studentDTO.getAddress(),
                    studentDTO.getDate_of_birth(),
                    studentDTO.getGender(),
                    this.passwordEncoder.encode(studentDTO.getPassword())
            );
            student = studentRepo.save(student);

            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .statusCode(200)
                            .status("Success")
                            .reason("OK")
                            .data(user)
                            .build(),
                    HttpStatus.OK
            );
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .statusCode(200)
                            .status("Failed")
                            .reason("Email already registered")
                            .data(null)
                            .build(),
                    HttpStatus.OK
            );
        }

    }


    @Override
    public ResponseEntity<ApiResponse> findUserByEmail(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user != null) {
            Student student = studentRepo.findByUserId(user.getUserId());
            if(passwordEncoder.matches(loginDTO.getPassword(), student.getPassword())){
                return new ResponseEntity<>(
                        ApiResponse.builder()
                                .statusCode(200)
                                .status("Success")
                                .reason("OK")
                                .data(user)
                                .build(),
                        HttpStatus.OK
                );
            }else{
                return new ResponseEntity<>(
                        ApiResponse.builder()
                                .statusCode(200)
                                .status("Failed")
                                .reason("Password is wrong")
                                .data(user)
                                .build(),
                        HttpStatus.OK
                );
            }
        } else {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .statusCode(200)
                            .status("Failed")
                            .reason("Email not found")
                            .data(null)
                            .build(),
                    HttpStatus.OK
            );
        }
    }


    @Override
    public ResponseEntity<ApiResponse> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(userList)
                        .build(),
                HttpStatus.OK
        );
    }


    @Override
    public ResponseEntity<ApiResponse> findAllUsersExceptThisUserId(int userId) {
        List<User> list = userRepository.findAllUsersExceptThisUserId(userId);

        return new ResponseEntity<>(
                ApiResponse.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(list)
                        .build(),
                HttpStatus.OK
        );
    }


    @Override
    public ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(int user1Id, int user2Id) {
        int conversationId;
        Optional<User> user1 = userRepository.findById(user1Id);
        Optional<User> user2 = userRepository.findById(user2Id);

        if (user1.isEmpty() || user2.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .statusCode(200)
                            .status("Failed")
                            .reason("User not found")
                            .data(null)
                            .build(),
                    HttpStatus.OK
            );
        }

        Optional<Conversation> existingConversation = conversationRepository.findConversationByUsers(user1.get(), user2.get());
        if (existingConversation.isPresent()) {
            conversationId = existingConversation.get().getConversationId();
        } else {
            Conversation newConversation = new Conversation();
            newConversation.setUser1(user1.get());
            newConversation.setUser2(user2.get());
            Conversation savedConversation = conversationRepository.save(newConversation);
            conversationId = savedConversation.getConversationId();
        }
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(conversationId)
                        .build(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<ApiResponse> findUsersByUserMail(String userMail) {
        return null;
    }
}
