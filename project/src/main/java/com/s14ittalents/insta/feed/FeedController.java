package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.feed.dto.FeedDTO;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedController extends AbstractController {

    @Autowired
    private FeedService feedService;

    @GetMapping(value = {"/feed", "/feed/{page:[0-9]+}"})
    public FeedDTO getFeed(@RequestParam(defaultValue = "false") boolean orderPostsByAsc,
                           @RequestParam(defaultValue = "false") boolean orderStoriesByAsc, @PathVariable(required = false) Integer page) {
        long userId = getLoggedUserId();
        return new FeedDTO(feedService.getMyStory(userId),feedService.getStoriesOfFollowedUsers(userId, orderStoriesByAsc),
                feedService.getPostsOfFollowedUsers(userId, orderPostsByAsc, page==null?0:(page<=0?0:page)));
    }

}
