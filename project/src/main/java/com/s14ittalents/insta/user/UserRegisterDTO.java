package com.s14ittalents.insta.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class UserRegisterDTO {
    private long id;
    private boolean activityStatus;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String firstName;
    private String lastName;
    private MultipartFile profilePicture;
    private String bio;
    private String gender;
    private String phoneNum;
    private LocalDateTime createdAt;
    private LocalDateTime dateOfBirth;
}
