package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedController extends AbstractController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/feed")
    public List<FeedPostDTO> getFeed() {
        long userId = getLoggedUserId();
        return feedService.getPostsOfFollowedUsers(userId);
    }

}
