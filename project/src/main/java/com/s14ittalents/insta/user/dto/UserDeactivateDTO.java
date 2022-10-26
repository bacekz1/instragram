package com.s14ittalents.insta.user.dto;

import lombok.Data;

@Data
public class UserDeactivateDTO {
    private String username;
    private String password;
    private String confirmPassword;
}
