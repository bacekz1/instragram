package com.s14ittalents.insta.user;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String bio;
    private String gender;
    private String phoneNum;
    private boolean privateAccount;
}
