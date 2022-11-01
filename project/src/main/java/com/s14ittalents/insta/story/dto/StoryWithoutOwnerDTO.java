package com.s14ittalents.insta.story.dto;

import com.s14ittalents.insta.content.ContentWithoutUser;
import com.s14ittalents.insta.location.LocationWithoutPost;
import com.s14ittalents.insta.user.dto.UserWithoutPostsDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class StoryWithoutOwnerDTO {
    private long id;
    private String caption;
    private boolean is_deleted;
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
