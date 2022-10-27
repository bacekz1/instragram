package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Setter
@Getter
public class FeedDTO {
    private List<PostWithoutOwnerDTO> recentPostsFromFollowedUsers;
    private List<PostWithoutOwnerDTO> recentStoriesFromFollowedUsers;
}
