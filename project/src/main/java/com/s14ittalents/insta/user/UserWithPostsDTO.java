package com.s14ittalents.insta.user;

import com.s14ittalents.insta.post.PostWithoutOwnerDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserWithPostsDTO {
    private long id;
    private boolean activity_status;
    private String username;
    private String email;
    private String first_name;
    private String last_name;
    private String profile_picture;
    private String bio;
    private String gender;
    private String phone_num;
    private List<PostWithoutOwnerDTO> posts;
}
