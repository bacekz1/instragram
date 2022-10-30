package com.s14ittalents.insta.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDeleteDTO {
    private String passwordOfAdmin;
    private String confirmPasswordOfAdmin;
}
