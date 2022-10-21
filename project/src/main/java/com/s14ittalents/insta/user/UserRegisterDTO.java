package com.s14ittalents.insta.user;

import lombok.Data;

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
    private String profilePicture;
    private String bio;
    private String gender;
    private String phoneNum;
}
