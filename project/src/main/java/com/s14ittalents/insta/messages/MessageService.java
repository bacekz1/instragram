package com.s14ittalents.insta.messages;


import com.s14ittalents.insta.util.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends AbstractService {
    @Autowired
    private MessageRepository messageRepository;
    
    public List<MessageOnlyIdDTO> getSentMessages(long userId) {
        return messageRepository.findAllBySenderId(userId)
                .stream()
                .map(m -> modelMapper.map(m, MessageOnlyIdDTO.class))
                .collect(Collectors.toList());
    }
    
    public List<MessageOnlyIdDTO> getReceivedMessages(long userId) {
        return messageRepository.findAllByReceiverId(userId)
                .stream()
                .map(m -> modelMapper.map(m, MessageOnlyIdDTO.class))
                .collect(Collectors.toList());
    }
    
    public MessageOnlyIdDTO sendMessage(long loggedUserId, String username) {
        Message message = new Message();
        message.setReceiverId(getUserByUsername(username).getId());
        message.setSenderId(getUserById(loggedUserId).getId());
        message.setSeen(false);
        message.setSendDate(LocalDateTime.now());
        messageRepository.save(message);
        return modelMapper.map(message, MessageOnlyIdDTO.class);
    }
}
