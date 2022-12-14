package com.s14ittalents.insta.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s14ittalents.insta.hashtag.dto.HashtagWithoutPost;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.dto.UserWithoutPostsDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResultDTO {
    private List<PostWithoutOwnerDTO> posts;
    private List<PostWithoutOwnerDTO> stories;
    private UserWithoutPostsDTO user;
    private List<UserWithoutPostsDTO> users;
    private List<HashtagWithoutPost> hashtags;
}
