package com.s14ittalents.insta.post;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import lombok.Data;
import java.util.List;

@Data
public class PostWithoutOwnerDTO {
    private long id;
    private long user_id;
    private String caption;
    private Long location_id;
    private boolean is_deleted;
    List<Comment> comments;
    private UserWithoutPostsDTO owner;
}
