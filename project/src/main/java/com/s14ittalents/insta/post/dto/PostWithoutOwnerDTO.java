package com.s14ittalents.insta.post.dto;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.content.ContentWithoutUser;
import com.s14ittalents.insta.location.LocationWithoutPost;
import com.s14ittalents.insta.user.dto.UserWithoutPostsDTO;
import lombok.Data;


import java.util.List;

@Data
public class PostWithoutOwnerDTO {
    private long id;
    private long user_id;
    private String caption;
    private Long location_id;
    private boolean is_deleted;
    private List<Comment> comments;
    private UserWithoutPostsDTO owner;
    private List<ContentWithoutUser> contents;
    private LocationWithoutPost location;
    private List<UserWithoutPostsDTO> likes;
    
    public Integer getLikes() {
        if (likes != null) {
            return likes.size();
        } else {
            return 0;
        }
    }
}
