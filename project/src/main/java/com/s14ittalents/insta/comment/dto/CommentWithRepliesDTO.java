package com.s14ittalents.insta.comment.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.s14ittalents.insta.user.dto.UserWithoutPostsDTO;
import lombok.Data;

import java.util.List;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CommentWithRepliesDTO {
    private long id;
    private String comment;
    private List<CommentWithRepliesDTO> replies;
    private List<UserWithoutPostsDTO> likes;

    public Integer getLikes() {
        if (likes != null) {
            return likes.size();
        } else {
            return 0;
        }
    }
}
