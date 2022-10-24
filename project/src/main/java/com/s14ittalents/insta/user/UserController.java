package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.UserNotCreatedException;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.REMOTE_IP;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/{username}")
    UserNoPasswordDTO getUser(@PathVariable String username) {
        return userService.getUserByUsernameToDTO(username);
    }

    @PostMapping()
    UserNoPasswordDTO createUser(@ModelAttribute UserRegisterDTO user) {
        if(session.getAttribute("id") != null) {
            throw new BadRequestException("You are already logged in" +
                    ", logout first if you wish to create another account");
        }

        return userService.createUser(user);
    }
    @PutMapping()
    UserNoPasswordDTO updateUser(@RequestBody UserUpdateDTO user) {
        long userId = getLoggedUserId();
        return userService.updateUser(user, userId);
    }
    
    @DeleteMapping()
    String deleteUser(@RequestBody UserDeleteDTO user) {
        long userId = getLoggedUserId();
        return userService.deleteUser(userId, user);
    }

    @PostMapping("/login")
    UserOnlyMailAndUsernameDTO loginUser(@RequestBody UserLoginDTO user) {;
        if(session.getAttribute("logged") != null) {
            throw new UserNotCreatedException("You are already logged in");
        }
        UserOnlyMailAndUsernameDTO user1 = userService.loginUser(user);
        session.setAttribute("logged", true);
        session.setAttribute("id", userService.getIdFromMailUsernameDTO(user1));
        System.out.println(session.getAttribute("id"));
        session.setAttribute(REMOTE_IP, request.getRemoteAddr());
        return user1;
    }
    
    @PostMapping("/logout")
    String logoutUser() {
        session.invalidate();
        return "You have been logged out";
    }
    
    
    @PutMapping("/{username}/pfp")
    public String uploadProfilePicture(@ModelAttribute(value = "file") MultipartFile file) {
        if(getLoggedUserId()>0){
            return userService.updateProfilePicture(file, getLoggedUserId());
        }else {
            throw new BadRequestException("Something went wrong");
        }
    }
    
    @PutMapping("/{username}/pfp/default")
    public String setDefaultProfilePicture() {
        if(getLoggedUserId()>0){
            return userService.setDefaultProfilePicture(getLoggedUserId());
        }else {
            throw new BadRequestException("Something went wrong");
        }
    }
    
    @PostMapping("/follow/{username}")
    public String followUser(@PathVariable String username) {
        return userService.followUser(username, getLoggedUserId());
    }
    
    @GetMapping("/followers")
    public List<UserOnlyMailAndUsernameDTO> getFollowers() {
        return userService.getFollowers(getLoggedUserId());
    }
    @GetMapping("/following")
    public List<UserOnlyMailAndUsernameDTO> getFollowing() {
        return userService.getFollowing(getLoggedUserId());
    }
    
    @PutMapping("/{username}/badUser")
    public String banUser(@PathVariable String username) {
        return userService.banUser(getLoggedUserId(),username);
    }
    
    /*
    if get logged user id >  -> throw new BadRequestException("you are already logged in");
    
    

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

