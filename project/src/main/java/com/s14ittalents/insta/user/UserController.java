package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.ExceptionController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{username}")
public class UserController extends ExceptionController {
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    UserNoPasswordDTO getUser(@PathVariable String username) {
        
        UserNoPasswordDTO dto = modelMapper.map(userRepository.findByUsername(username), UserNoPasswordDTO.class);
        return dto;
    }
    
    @PostMapping
    User createUser(@RequestBody User user) {
        System.out.println(user.getUsername());
        return userRepository.save(user);
    }
}
