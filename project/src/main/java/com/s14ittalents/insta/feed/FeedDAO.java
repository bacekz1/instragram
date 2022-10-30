package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.feed.dto.FeedMyStoryWithContentDTO;
import com.s14ittalents.insta.feed.dto.FeedPostWithContentDTO;
import com.s14ittalents.insta.feed.dto.FeedStoryWithContentDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FeedDAO {
    int countAllRowsForPostSelect(long loggedUserId);
    @Transactional
    List<FeedPostWithContentDTO> getPostsOfFollowedUsers(long loggedUserId, boolean order, long page);
    @Transactional
    List<FeedStoryWithContentDTO> getStoriesOfFollowedUsers(long loggedUserId, boolean order);
    @Transactional
    FeedMyStoryWithContentDTO getMyStory(long loggedUserId);
}
