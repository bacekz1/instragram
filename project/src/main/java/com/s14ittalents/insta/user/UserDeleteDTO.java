package com.s14ittalents.insta.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDeleteDTO {
    private String password;
    private String confirmPassword;
}
