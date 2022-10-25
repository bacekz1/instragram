package com.s14ittalents.insta.user;

import lombok.Data;

@Data
public class UserChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
