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
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/{username}")
    UserNoPasswordDTO getUser(@PathVariable String username) {
        return userService.getUserByUsernameToDTO(username);
    }

    @PostMapping()
    UserNoPasswordDTO createUser(@ModelAttribute UserRegisterDTO user) {
        validateEmail(user.getEmail());
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        validatePassword(user.getConfirmPassword());
        if(session.getAttribute("id") != null) {
            throw new BadRequestException("You are already logged in" +
                    ", logout first if you wish to create another account");
        }
//        UserRegisterDTO user = new UserRegisterDTO();
//        user.setUsername(request.getParameterMap().get("username")[0]);
//        user.setPassword(request.getParameterMap().get("password")[0]);

        return userService.createUser(user);
    }
    @PutMapping()
    UserNoPasswordDTO updateUser(@RequestBody UserUpdateDTO user) {
        long userId = getLoggedUserId();
        validateEmail(user.getEmail());
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        return userService.updateUser(user, userId);
    }
    
    @DeleteMapping()
    String deleteUser(@RequestBody UserDeleteDTO user) {
        long userId = getLoggedUserId();
        validatePassword(user.getPassword());
        validatePassword(user.getConfirmPassword());
        return userService.deleteUser(userId, user);
    }

    @PostMapping("/login")
    UserOnlyMailAndUsernameDTO loginUser(@RequestBody UserLoginDTO user) {
        if(user.getUsername().indexOf('@') != -1) {
            validateEmail(user.getUsername());
        } else {
            validateUsername(user.getUsername());
        }
        validatePassword(user.getPassword());
        Optional<User> user1 = userService.checkIfUserExists(user);
        if(user1.get().isBanned()) {
            throw new BadRequestException("You are banned");
        }
        if(session.getAttribute("logged") != null) {
            throw new UserNotCreatedException("You are already logged in");
        }
        UserOnlyMailAndUsernameDTO user2 = userService.loginUser(user1
                .orElseThrow(() -> new BadRequestException("Try again")), user.getPassword());
        session.setAttribute("logged", true);
        session.setAttribute("id", user1.get().getId());
        System.out.println(session.getAttribute("id"));
        session.setAttribute(REMOTE_IP, request.getRemoteAddr());
        return user2;
    }
    
    @PostMapping("/logout")
    String logoutUser() {
        session.invalidate();
        return "You have been logged out";
    }
    
    
    @PutMapping("/{username}/pfp")
    public String uploadProfilePicture(@RequestParam(value = "file") MultipartFile file) {
        if(getLoggedUserId()>0){
            if(file.getSize()>5242880){
                throw new BadRequestException("File size is too big, must be below 5MB");
            }
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
        User userToFollow = userRepository.findByUsername(username.toLowerCase().trim())
                .orElseThrow(() -> new DataNotFoundException("Follow unsuccessful, user not found"));
        if(userToFollow.getId() == getLoggedUserId()) {
            throw new BadRequestException("You cannot follow yourself");
        }
        return userService.followUser(userToFollow, getLoggedUserId());
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

