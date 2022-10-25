package com.s14ittalents.insta.search;

import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResultDTO {
    private List<PostWithoutOwnerDTO> posts;
    private List<PostWithoutOwnerDTO> stories;
    private UserWithoutPostsDTO user;
}
