package com.s14ittalents.insta.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class FeedDTO {
    private FeedMyStoryWithContentDTO myStory;
    private List<FeedStoryWithContentDTO> storiesFromFollowedUsers;
    private List<FeedPostWithContentDTO> postsFromFollowedUsers;
}
