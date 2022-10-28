package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.feed.dto.FeedDTO;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedController extends AbstractController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/feed")
    public FeedDTO getFeed(@Param("orderPostsByAsc") boolean orderPostsByAsc,
                           @Param("orderStoriesByAsc") boolean orderStoriesByAsc, @Param("page") int page) {
        long userId = getLoggedUserId();
        return new FeedDTO(feedService.getMyStory(userId),feedService.getStoriesOfFollowedUsers(userId, orderStoriesByAsc),
                feedService.getPostsOfFollowedUsers(userId, orderPostsByAsc, page));
    }

}
