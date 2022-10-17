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
    UserNoPasswordDTO getUser(@PathVariable String username) {
        UserNoPasswordDTO dto = modelMapper.map(userRepository.findByUsername(username), UserNoPasswordDTO.class);
        return dto;
    }
    
}
