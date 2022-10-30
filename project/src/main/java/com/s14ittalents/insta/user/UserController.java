package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.UserNotCreatedException;
import com.s14ittalents.insta.user.dto.*;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.s14ittalents.insta.exception.Constant.REMOTE_IP;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/username/{username}")
    UserNoPasswordDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsernameToDTO(username);
    }
    
    @GetMapping("/{id:[0-9]+}")
    UserNoPasswordDTO getUserById(@PathVariable long id) {
        return userService.getUserByIdToDTO(id);
    }

    @PostMapping()
    UserNoPasswordDTO createUser(@ModelAttribute UserRegisterDTO user) {
        String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
        if(session.getAttribute("id") != null) {
            throw new BadRequestException("You are already logged in" +
                    ", logout first if you wish to create another account");
        }

        return userService.createUser(user,siteURL);
    }
    @PostMapping("/passwordRecovery")
    String getNewPassword(@ModelAttribute UserOnlyMailAndUsernameDTO user) {
        String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
        if(session.getAttribute("id") != null) {
            throw new BadRequestException("You are already logged in and you can change your password from your profile");
        }
        
        return userService.fixForgottenPassword(user,siteURL);
    }
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "User verified successfully";
        } else {
            return "Verification failed";
        }
    }
    @PutMapping("/passwordRecovery")
    public String fixPassword(@Param("code") String code, @RequestBody UserPasswordResetDTO userNewPassword) {
        if (userService.setNewPasswordForForgottenPassword(code,userNewPassword)) {
            return "Password reset successfully";
        } else {
            return "Password reset failed";
        }
    }
    //todo: fully test /accountCredentials endpoint and /accountInfo endpoint
    @PutMapping("/accountCredentials")
    UserNoPasswordDTO updateUserCred(@ModelAttribute UserUpdateDTO user) {
        long userId = getLoggedUserId();
        return userService.updateUserCredentials(user, userId);
    }
    
    
    @PutMapping("/accountInfo")
    UserNoPasswordDTO updateUser(@ModelAttribute UserEditDTO user) {
        long userId = getLoggedUserId();
        return userService.editUserInfo(user, userId);
    }
    @DeleteMapping("/account/{account}")
    String adminDeleteUser(@ModelAttribute UserDeleteDTO user, @PathVariable String account) {
        long userId = getLoggedUserId();
        boolean isUsername = false;
        if (account.lastIndexOf("@")==-1) {
            isUsername = true;
        }
        return userService.deleteUser(userId, user, account, isUsername);
    }
    
    @DeleteMapping("/{id:[0-9]+}")
    String adminDeleteUser(@ModelAttribute UserDeleteDTO user, @PathVariable long id) {
        long userId = getLoggedUserId();
        return userService.deleteUser(userId, user, userService.getUserById(id).getUsername(),true);
    }
    
    @DeleteMapping("")
    String deactivateUser(@RequestBody UserDeactivateDTO user) {
        long userId = getLoggedUserId();
        session.invalidate();
        return userService.deactivateUser(userId, user);
    }

    @PostMapping("/login")
    UserOnlyMailAndUsernameDTO loginUser(@RequestBody UserLoginDTO user) {
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
    
    //mapping for change password
    @PutMapping("/password")
    String changePassword(@RequestBody UserChangePasswordDTO user) {
        long userId = getLoggedUserId();
        return userService.changePassword(user, userId);
    }
    
    
    @PutMapping("/pfp")
    public String uploadProfilePicture(@ModelAttribute(value = "file") MultipartFile file) {
        if(getLoggedUserId()>0){
            return userService.updateProfilePicture(file, getLoggedUserId());
        }else {
            throw new BadRequestException("Something went wrong");
        }
    }
    
    @PutMapping("/pfp/default")
    public String setDefaultProfilePicture() {
        if(getLoggedUserId()>0){
            return userService.setDefaultProfilePicture(getLoggedUserId());
        }else {
            throw new BadRequestException("Something went wrong");
        }
    }
    
    @PostMapping("/username/{username}/follow")
    public String followUser(@PathVariable String username) {
        return userService.followUser(username, getLoggedUserId());
    }
    
    @PostMapping("/{id:[0-9]+}/follow")
    public String followUser(@PathVariable long id) {
        return userService.followUser(userService.getUserById(id).getUsername(), getLoggedUserId());
    }
    
    @GetMapping("/followers")
    public List<UserOnlyMailAndUsernameDTO> getFollowers() {
        return userService.getFollowers(getLoggedUserId());
    }
    @GetMapping("/following")
    public List<UserOnlyMailAndUsernameDTO> getFollowing() {
        return userService.getFollowing(getLoggedUserId());
    }
    
    @GetMapping("/{id:[0-9]+}/followers")
    public List<UserOnlyMailAndUsernameDTO> getFollowersOfUserById(@PathVariable long id) {
        return userService.getFollowers(id);
    }
    @GetMapping("/{id:[0-9]+}/following")
    public List<UserOnlyMailAndUsernameDTO> getFollowingOfUserById(@PathVariable long id) {
        return userService.getFollowing(id);
    }
    
    @PutMapping("username/{username}/badUser")
    public String banUser(@PathVariable String username) {
        return userService.banUser(getLoggedUserId(),username,null);
    }
    @PutMapping("/{id:[0-9]+}/badUser")
    public String banUserById(@PathVariable long id) {
        return userService.banUser(getLoggedUserId(),null,id);
    }
}

