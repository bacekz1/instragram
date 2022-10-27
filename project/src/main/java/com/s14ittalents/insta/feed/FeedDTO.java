package com.s14ittalents.insta.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Data
@AllArgsConstructor
public class FeedDTO {
    private List<FeedStoryWithContentDTO> storiesFromFollowedUsers;
    private List<FeedPostWithContentDTO> postsFromFollowedUsers;
}
