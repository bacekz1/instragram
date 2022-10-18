package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.UserNotCreatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService{
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    
    UserNoPasswordDTO getUserByUsername(String username) {
        Optional<User> user = Optional.of(userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found")));
        return modelMapper.map(user, UserNoPasswordDTO.class);
    }
    
    UserNoPasswordDTO createUser(UserRegisterDTO user) {
        if (Objects.equals(user.getPassword(), user.getConfirm_password())) {
            userRepository.save(modelMapper.map(user, User.class));
            return modelMapper.map(user, UserNoPasswordDTO.class);
        }
        else {
            throw new UserNotCreatedException("Passwords do not match");
        }
    }
    
    UserOnlyMailAndUsernameDTO loginUser(UserLoginDTO user) {
        Optional<User> user1 = Optional.of(userRepository.findByEmailAndPasswordOrUsernameAndPassword
                (user.getUsername(), user.getPassword(), user.getUsername(), user.getPassword())
                .orElseThrow(() -> new DataNotFoundException("User not found")));
        return modelMapper.map(user1, UserOnlyMailAndUsernameDTO.class);
    }
    
}
