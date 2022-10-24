package com.s14ittalents.insta.messages;


import com.s14ittalents.insta.util.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
