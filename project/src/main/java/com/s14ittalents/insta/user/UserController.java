package com.s14ittalents.insta.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{username}")
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    UserDTO getUser(@PathVariable String username) {
        UserDTO dto = modelMapper.map(userRepository.findByUsername(username), UserDTO.class);
        return dto;
    }
    
}
