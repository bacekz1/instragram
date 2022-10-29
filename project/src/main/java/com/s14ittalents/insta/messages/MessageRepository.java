package com.s14ittalents.insta.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAllBySenderId(long id);
    List<Message> findAllByReceiverId(long id);
    
    List<Message> findAllBySenderIdAndReceiverId(long senderId, long receiverId);
}
