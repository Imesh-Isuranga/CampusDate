package com.campus_date.controller;


import com.campus_date.dto.MessageRequest;
import com.campus_date.service.MessageSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Controller class that handles real-time messaging using WebSocket communication.
 * Routes:
 * - /user: Send user conversations to a specific user by their user ID through a web socket.
 * - /conv: Send messages of a specific conversation to the connected users through a web socket.
 * - /sendMessage: Save a new message using a web socket.
 * - /deleteConversation: Delete a conversation by its unique conversation ID using a web socket.
 * - /deleteMessage: Delete a message by its unique message ID within a conversation using a web socket.
 */
@RequiredArgsConstructor
@Controller
public class MessageSocketController {
    private final MessageSocketService socketService;

    @MessageMapping("/user")
    public void sendUserConversationByUserId(int userId) {
        socketService.sendUserConversationByUserId(userId);
    }


    @MessageMapping("/conv")
    public void sendMessagesByConversationId(int conversationId) {
        socketService.sendMessagesByConversationId(conversationId);
    }


    @MessageMapping("/sendMessage")
    public void saveMessage(MessageRequest message) {
        socketService.saveMessage(message);
    }


    @MessageMapping("/deleteConversation")
    public void deleteConversation(Map<String, Object> payload) {
        int conversationId = (int) payload.get("conversationId");
        int user1 = (int) payload.get("user1Id");
        int user2 = (int) payload.get("user2Id");
        socketService.deleteConversationByConversationId(conversationId);
        socketService.sendUserConversationByUserId(user1);
        socketService.sendUserConversationByUserId(user2);
    }


    @MessageMapping("/deleteMessage")
    public void deleteMessage(Map<String, Object> payload) {
        int conversationId = (int) payload.get("conversationId");
        int messageId = (int) payload.get("messageId");
        socketService.deleteMessageByMessageId(conversationId, messageId);
    }
}

