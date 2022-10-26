package com.s14ittalents.insta.user.dto;

import lombok.Data;

@Data
public class UserNoPasswordDTO {
    private long id;
    private boolean activity_status;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String bio;
    private String gender;
    private String phoneNum;
}
