package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.ExceptionController;
import com.s14ittalents.insta.exception.UserNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    
    @PostMapping("/login")
    UserOnlyMailAndUsernameDTO loginUser(@RequestBody UserLoginDTO user, HttpSession session) {
        session.setAttribute("logged", true);
        return userService.loginUser(user);
    }
    
    @PostMapping("/logout")
    void logoutUser(HttpSession session) {
        session.invalidate();
    }
    /*
    if get logged user id >  -> throw new BadRequestException("you are already logged in");
    
    
    logUser(HttpSession session,int id) {
        session.setAttribute("logged", true);
        session.setAttribute("id", id);
        sessiom.setAttribute("REMOTE_IP", request.getRemoteAddr());
        
        // check also if ip matches
        -servlet request get remote host teq.getSession
        String ip = req.getRemoteAddr();
        };
     */
    
    /*PostMapping("/{username}/profile_picture"){
    --url params - form data -come as key value - req get param
    @RequestParam("profile_picture") MultipartFile file
    multipart resolver
    
    //upload to files (make unique name) user id + timestamp
    //get the file name
    //upload filename to user.profile_picture
    
    }
    
     */
}

