package com.campus_date.service.impl;

import com.campus_date.dao.ConversationRepository;
import com.campus_date.dao.MessageRepository;
import com.campus_date.dao.UserRepository;
import com.campus_date.dto.ConversationResponse;
import com.campus_date.dto.MessageRequest;
import com.campus_date.dto.MessageResponse;
import com.campus_date.dto.WebSocketResponse;
import com.campus_date.entity.Conversation;
import com.campus_date.entity.Message;
import com.campus_date.entity.User;
import com.campus_date.service.MessageSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;

/**
 * Implementation of the MessageSocketService interface that handles real-time messaging functionality using web sockets.
 */
@Service
@RequiredArgsConstructor
public class MessageSocketServiceImpl implements MessageSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    /**
     * Send user conversations to a specific user by their user ID through a web socket.
     *
     * @param userId The ID of the user for whom to send conversations.
     */
    @Override
    public void sendUserConversationByUserId(int userId) {
        List<ConversationResponse> conversation = conversationRepository.findConversationsByUserId(userId);
        System.out.println("111111111111111111111111111111111111111111****************");
        System.out.println(conversation);
        messagingTemplate.convertAndSend(
                "/topic/user/".concat(String.valueOf(userId)),
                WebSocketResponse.builder()
                        .type("ALL")
                        .data(conversation)
                        .build()
        );
    }

    /**
     * Send messages of a specific conversation to the connected users through a web socket.
     *
     * @param conversationId The ID of the conversation for which to send messages.
     */
    @Override
    public void sendMessagesByConversationId(int conversationId) {
        System.out.println("22222222222222222222222222222222***********************");
        Conversation conversation = new Conversation();
        System.out.println(conversation);
        conversation.setConversationId(conversationId);
        List<Message> messageList = messageRepository.findAllByConversationOrderByTimestampDesc(conversation);
        List<MessageResponse> messageResponseList = messageList.stream()
                .map((message -> MessageResponse.builder()
                        .messageId(message.getMessageId())
                        .message(message.getMessage())
                        .timestamp(Date.from(message.getTimestamp().atZone(ZoneId.systemDefault()).toInstant()))
                        .senderId(message.getSender().getUserId())
                        .receiverId(message.getReceiver().getUserId())
                        .build())
                ).toList();
        messagingTemplate.convertAndSend("/topic/conv/".concat(String.valueOf(conversationId)), WebSocketResponse.builder()
                .type("ALL")
                .data(messageResponseList)
                .build()
        );
    }

    /**
     * Save a new message using a web socket.
     *
     * @param msg The MessageRequest object containing the message details to be saved.
     */
    @Override
    public void saveMessage(MessageRequest msg) {
        User sender = userRepository.findById(msg.getSenderId()).get();
        User receiver = userRepository.findById(msg.getReceiverId()).get();
        Conversation conversation = conversationRepository.findConversationByUsers(sender, receiver).get();
        Message newMessage = new Message();
        newMessage.setMessage(msg.getMessage());
        newMessage.setTimestamp(msg.getTimestamp());
        newMessage.setConversation(conversation);
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
        Message savedMessage = messageRepository.save(newMessage);
        // notify listener
        MessageResponse res = MessageResponse.builder()
                .messageId(savedMessage.getMessageId())
                .message(savedMessage.getMessage())
                .timestamp(Date.from(savedMessage.getTimestamp().atZone(ZoneId.systemDefault()).toInstant()))
                .senderId(savedMessage.getSender().getUserId())
                .receiverId(savedMessage.getReceiver().getUserId())
                .build();
        messagingTemplate.convertAndSend("/topic/conv/".concat(msg.getConversationId().toString()),
                WebSocketResponse.builder()
                        .type("ADDED")
                        .data(res)
                        .build()
        );
        sendUserConversationByUserId(msg.getSenderId());
        sendUserConversationByUserId(msg.getReceiverId());
    }

    /**
     * Delete a conversation by its unique conversation ID using a web socket.
     *
     * @param conversationId The ID of the conversation to be deleted.
     */
    @Transactional
    @Override
    public void deleteConversationByConversationId(int conversationId) {
        Conversation c = new Conversation();
        c.setConversationId(conversationId);
        messageRepository.deleteAllByConversation(c);
        conversationRepository.deleteById(conversationId);
    }

    /**
     * Delete a message by its unique message ID within a conversation using a web socket.
     *
     * @param conversationId The ID of the conversation to notify its listener.
     * @param messageId      The ID of the message to be deleted.
     */
    @Override
    public void deleteMessageByMessageId(int conversationId, int messageId) {
        messageRepository.deleteById(messageId);
        // notify listener
        sendMessagesByConversationId(conversationId);
    }
}
