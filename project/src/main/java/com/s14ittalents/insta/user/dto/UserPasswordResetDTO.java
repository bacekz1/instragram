package com.s14ittalents.insta.user.dto;

import lombok.Data;

@Data
public class UserPasswordResetDTO {
    String password;
    String confirmPassword;
}
