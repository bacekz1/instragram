package com.s14ittalents.insta.post.dto;

import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import lombok.Data;

@Data
public class PostWithOwnerDTO {
    private long id;
    private long user_id;
    private String caption;
    private Long location_id;
    private boolean is_deleted;
    private UserWithoutPostsDTO owner;
}

