package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.util.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService extends AbstractService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional
    public List<FeedPostWithContentDTO> getPostsOfFollowedUsers(long loggedUserId, boolean order) {
        String insertOrder = order ? "ASC" : "DESC";
        String sql = "SELECT \n" + "    u.profile_picture,\n"
                + "    u.username,\n"
                + "    u.first_name,\n"
                + "    u.last_name,\n"
                + "    p.id AS post_id,\n"
                + "    p.caption,\n" + "    l.name AS location,\n"
                + "    p.created_time\n"
                + "FROM\n"
                + "    users AS u\n"
                + "        LEFT JOIN\n"
                + "    following AS f ON (u.id = f.user_id)\n"
                + "        LEFT JOIN\n" + "    post AS p ON (p.user_id = u.id)\n"
                + "        LEFT JOIN\n" + "    locations AS l ON (p.location_id = l.id)\n"
                + "WHERE\n" + "    f.follower_id = '"+ (int) loggedUserId +"'\n" + "        AND u.is_banned = '0'\n"
                + "        AND u.is_deactivated = '0'\n" + "        AND p.expiration_time IS NULL\n"
                + "        AND p.is_deleted = '0'\n"
                + "ORDER BY p.created_time "+insertOrder+"\n" + "LIMIT 20;";
        List<FeedPostDTO> posts =  jdbcTemplate.query(sql, (rs, rowNum) -> new FeedPostDTO(
                rs.getString("profile_picture"),
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getLong("post_id"),
                rs.getString("caption"),
                rs.getString("location"),
                (rs.getTimestamp("created_time").toLocalDateTime()
        )));
        List<FeedPostWithContentDTO> postsWithContent = new ArrayList<>();
        for (FeedPostDTO post : posts) {
            String sql2 = "SELECT \n" + "    c.media\n"
                    + "FROM\n"
                    + "    post AS p\n"
                    + "        LEFT JOIN\n"
                    + "    post_content AS c ON (p.id = c.post_id)\n"
                    + "WHERE\n"
                    + "    p.id = '"+ post.getPost_id() +"'";
            List<FeedContentDTO> content = jdbcTemplate.query(sql2, (rs, rowNum) -> new FeedContentDTO(
                    rs.getString("media")
            ));
            String sql3 = "SELECT \n" + "    COUNT(post_id) AS count_likes\n"
                    + "FROM\n"
                    + "    post AS p\n"
                    + "        LEFT JOIN\n"
                    + "    like_post AS l ON (p.id = l.post_id)\n"
                    + "WHERE\n"
                    + "    p.id = '"+ post.getPost_id() +"'";
            int countLikes = jdbcTemplate.queryForObject(sql3, Integer.class);
            String sql4 = "SELECT \n" + "    COUNT(post_id) AS count_comments\n"
                    + "FROM\n"
                    + "    post AS p\n"
                    + "        LEFT JOIN\n"
                    + "    comments AS c ON (p.id = c.post_id)\n"
                    + "WHERE\n"
                    + "    p.id = '"+ post.getPost_id() +"'"
                    + "        AND c.is_deleted = '0'";
            int countComments = jdbcTemplate.queryForObject(sql4, Integer.class);
            postsWithContent.add(new FeedPostWithContentDTO(post,content,countLikes,countComments));
        }
        
        
        return postsWithContent;
    }
    
    @Transactional
    public List<FeedStoryWithContentDTO> getStoriesOfFollowedUsers(long loggedUserId, boolean order) {
        String insertOrder = order ? "ASC" : "DESC";
        String sql = "SELECT \n" + "    u.profile_picture,\n"
                + "    u.username,\n"
                + "    u.first_name,\n"
                + "    u.last_name,\n"
                + "    p.id AS post_id,\n"
                + "    p.caption,\n" + "    l.name AS location,\n"
                + "    p.created_time\n"
                + "FROM\n"
                + "    users AS u\n"
                + "        LEFT JOIN\n"
                + "    following AS f ON (u.id = f.user_id)\n"
                + "        LEFT JOIN\n" + "    post AS p ON (p.user_id = u.id)\n"
                + "        LEFT JOIN\n" + "    locations AS l ON (p.location_id = l.id)\n"
                + "WHERE\n" + "    f.follower_id = '"+ (int) loggedUserId +"'\n" + "        AND u.is_banned = '0'\n"
                + "        AND u.is_deactivated = '0'\n" + "        AND p.expiration_time IS NULL\n"
                + "        AND p.is_deleted = '0'\n"
                + "        AND p.expiration_time >= date(now())\n"
                + "ORDER BY p.created_time "+insertOrder+"\n" + "LIMIT 20;";
        List<FeedStoryDTO> stories = new ArrayList<>();
        stories =  jdbcTemplate.query(sql, (rs, rowNum) -> new FeedStoryDTO(
                rs.getString("profile_picture"),
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getLong("post_id"),
                rs.getString("caption"),
                rs.getString("location"),
                (rs.getTimestamp("created_time").toLocalDateTime()
                )));
        List<FeedStoryWithContentDTO> storiesWithContent = new ArrayList<>();
        for (FeedStoryDTO story : stories) {
            story.setTimePassed((Duration.between(story.getCreated_time(), LocalDateTime.now()).toHours()));
            String sql2 = "SELECT \n" + "    c.media\n"
                    + "FROM\n"
                    + "    post AS p\n"
                    + "        LEFT JOIN\n"
                    + "    post_content AS c ON (p.id = c.post_id)\n"
                    + "WHERE\n"
                    + "    p.id = '"+ story.getPost_id() +"'";
            List<FeedContentDTO> content = jdbcTemplate.query(sql2, (rs, rowNum) -> new FeedContentDTO(
                    rs.getString("media")
            ));
            storiesWithContent.add(new FeedStoryWithContentDTO(story,content));
        }
        
        return storiesWithContent;
    }
}
