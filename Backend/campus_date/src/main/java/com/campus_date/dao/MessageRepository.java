package com.campus_date.dao;


import com.campus_date.entity.Conversation;
import com.campus_date.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByConversationOrderByTimestampDesc(Conversation conversation);

    void deleteAllByConversation(Conversation conversation);
}
