package com.s14ittalents.insta.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{username}")
public class UserController {
    
    @Autowired
    UserRepository userRepository;

    @GetMapping
    User getUser(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }
    
}
