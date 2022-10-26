package com.s14ittalents.insta.user.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateDTO {
    private String username;
    private String email;
    private String password;
    private String newPassword;
    private String confirmPassword;
}
