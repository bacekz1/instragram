package com.s14ittalents.insta.post.dto;

import com.s14ittalents.insta.comment.dto.CommentWithRepliesDTO;
import com.s14ittalents.insta.content.ContentWithoutUser;
import com.s14ittalents.insta.location.LocationWithoutPost;
import com.s14ittalents.insta.user.dto.UserWithoutPostsDTO;
import lombok.Data;


import java.util.List;

@Data
public class PostWithoutOwnerDTO {
    private long id;
    private String caption;
    private boolean is_deleted;
    private List<CommentWithRepliesDTO> comments;
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
