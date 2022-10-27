package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class FeedService extends AbstractService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<FeedPostDTO> getPostsOfFollowedUsers(long loggedUserId) {
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
                + "ORDER BY p.created_time ASC";
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
//        List<FeedPostWithContentDTO> postWithContent= new FeedPostWithContentDTO();
//        for (FeedPostDTO post : posts) {
//            postWithContent.add(new FeedPostWithContentDTO(post, getPostContent(post.getPost_id())));
//        }
        return posts;
    }
}
