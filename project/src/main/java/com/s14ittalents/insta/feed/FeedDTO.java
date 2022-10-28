package com.s14ittalents.insta.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;

import java.util.List;


@Data
@AllArgsConstructor
public class FeedDTO {
    private FeedMyStoryWithContentDTO myStory;
    private List<FeedStoryWithContentDTO> storiesFromFollowedUsers;
    private List<FeedPostWithContentDTO> postsFromFollowedUsers;
}
