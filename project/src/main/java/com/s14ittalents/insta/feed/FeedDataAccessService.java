package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.feed.dto.FeedMyStoryWithContentDTO;
import com.s14ittalents.insta.feed.dto.FeedPostWithContentDTO;
import com.s14ittalents.insta.feed.dto.FeedStoryWithContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.s14ittalents.insta.exception.Constant.SQL_TO_COUNT_ALL_FROM_SELECTION_FOR_FEED;

@Repository
public class FeedDataAccessService implements FeedDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public int countAllRowsForPostSelect(long loggedUserId) {
        try {
            return jdbcTemplate.queryForObject(SQL_TO_COUNT_ALL_FROM_SELECTION_FOR_FEED, Integer.class, loggedUserId);
        } catch (NullPointerException | DataAccessException e) {
            return 0;
        }
    }

    @Transactional
    public List<FeedPostWithContentDTO> getPostsOfFollowedUsers(long loggedUserId, boolean order, long page) {
        return null;
    }
    @Transactional
    public List<FeedStoryWithContentDTO> getStoriesOfFollowedUsers(long loggedUserId, boolean order) {
        return null;
    }
    @Transactional
    public FeedMyStoryWithContentDTO getMyStory(long loggedUserId) {
        return null;
    }
}
