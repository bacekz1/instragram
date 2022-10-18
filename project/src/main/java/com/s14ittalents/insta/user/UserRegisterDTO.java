package com.s14ittalents.insta.user;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private long id;
    private boolean activity_status;
    private String username;
    private String password;
    private String confirm_password;
    private String email;
    private String first_name;
    private String last_name;
    private String profile_picture;
    private String bio;
    private String gender;
    private String phone_num;
}