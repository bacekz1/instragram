package com.s14ittalents.insta.messages;

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
    List<MessageOnlyIdDTO> getSentMessages() {
        return messageService.getSentMessages(getLoggedUserId());
    }
    
    @GetMapping("/receivedMessages")
    List<MessageOnlyIdDTO> getReceivedMessages() {
        return messageService.getReceivedMessages(getLoggedUserId());
    }
    
    PostMapping("/{username}")
    MessageOnlyIdDTO sendMessage(@PathVariable String username, @RequestBody MessageCreateDTO message) {
        
        return messageService.sendMessage(getLoggedUserId(), username);
    }
}
