package com.s14ittalents.insta.messages;

import com.s14ittalents.insta.messages.dto.MessageConversationDTO;
import com.s14ittalents.insta.messages.dto.MessageCreateDTO;
import com.s14ittalents.insta.messages.dto.MessageInfoDTO;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController extends AbstractController {
    
    @Autowired
    private MessageService messageService;
    
    @GetMapping("/sentMessages")
    List<MessageInfoDTO> getSentMessages() {
        return messageService.getSentMessages(getLoggedUserId());
    }
    
    @GetMapping("/receivedMessages")
    List<MessageInfoDTO> getReceivedMessages() {
        return messageService.getReceivedMessages(getLoggedUserId());
    }
    @PostMapping("/{username}")
    MessageInfoDTO sendMessage(@PathVariable String username, @RequestBody MessageCreateDTO message) {
        
        return messageService.sendMessage(getLoggedUserId(), username, message.getText());
    }
    @GetMapping("/conversation/{username}")
    MessageConversationDTO getConversation(@PathVariable String username) {
        return messageService.getConversation(getLoggedUserId(), username);
    }
}
