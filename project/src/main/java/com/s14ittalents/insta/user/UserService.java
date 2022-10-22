package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.UserNotCreatedException;
import com.s14ittalents.insta.util.AbstractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.REMOTE_IP;

@Service
public class UserService extends AbstractService {
    

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
        if (Objects.equals(user.getPassword(), user.getConfirmPassword())) {
            userRepository.save(modelMapper.map(user, User.class));
            return modelMapper.map(user, UserNoPasswordDTO.class);
        }
        else {
            throw new UserNotCreatedException("Passwords do not match");
        }
    }
    
    UserOnlyMailAndUsernameDTO loginUser(UserLoginDTO user, HttpSession session, HttpServletRequest request) {
        Optional<User> user1 = Optional.of(userRepository.findByEmailAndPasswordOrUsernameAndPassword
                (user.getUsername(), user.getPassword(), user.getUsername(), user.getPassword())
                .orElseThrow(() -> new DataNotFoundException("User not found")));
        session.setAttribute("logged", true);
        session.setAttribute("id", user1.get().getId());
        System.out.println(session.getAttribute("id"));
        session.setAttribute(REMOTE_IP, request.getRemoteAddr());
        request.getRemoteHost();
        return modelMapper.map(user1, UserOnlyMailAndUsernameDTO.class);
    }
    
    public UserNoPasswordDTO updateUser(UserUpdateDTO user, long userId,HttpSession session, HttpServletRequest req) {
        User user1 = getUserById(userId);
        if (getLoggedUserId(session,req) == userId) {
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setEmail(user.getEmail());
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            userRepository.save(user1);
            return modelMapper.map(user1, UserNoPasswordDTO.class);
        }
        else {
            throw new DataNotFoundException("User not found");
        }
    }
    //loginUser(n,int id) {

        
        // check also if ip matches
        //servlet request get remote host req.getSession
        
    //};
    
}
