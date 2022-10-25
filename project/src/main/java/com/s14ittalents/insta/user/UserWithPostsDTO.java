package com.s14ittalents.insta.user;

import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserWithPostsDTO {
    private long id;
    private boolean activityStatus;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String bio;
    private String gender;
    private String phoneNum;
    private List<PostWithoutOwnerDTO> posts;
}
