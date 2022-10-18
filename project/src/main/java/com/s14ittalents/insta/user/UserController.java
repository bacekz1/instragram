package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.UserNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController{
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/{username}")
    UserNoPasswordDTO getUser(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
    
    @PostMapping("/registration")
    UserNoPasswordDTO createUser(@RequestBody UserRegisterDTO user) {
        return userService.createUser(user);
    }
    
    
}

