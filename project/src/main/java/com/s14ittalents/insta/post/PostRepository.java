package com.s14ittalents.insta.post;

import com.s14ittalents.insta.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndDeletedIsFalseAndExpirationTimeIsNull(long id);

    Page<Post> findByOwnerIdAndDeletedIsFalseAndExpirationTimeIsNullOrderByCreatedTimeDesc(long id, Pageable pageable);
    
    List<Post> findAllByOwner(User owner);

    @Query(
            value = "SELECT * , p.id as postId, COUNT(post_hashtag_id)\n" +
                    "FROM post as p\n" +
                    "JOIN hashtags_posts as h ON p.id = h.post_hashtag_id\n" +
                    "JOIN hashtags as ha ON ha.id = h.tag_id\n" +
                    "LEFT JOIN like_post as l ON l.post_id = p.id\n" +
                    "WHERE tag_name like %:query% AND is_deleted = 0 AND expiration_time IS null\n" +
                    "GROUP BY post_hashtag_id\n" +
                    "ORDER BY post_hashtag_id DESC\n" +
                    "LIMIT 5",
            nativeQuery = true)
    List<Post> searchHashtagPosts(@Param("query") String query);

    @Query(
            value = "SELECT * , p.id as postId, COUNT(post_hashtag_id)\n" +
                    "FROM post as p\n" +
                    "JOIN hashtags_posts as h ON p.id = h.post_hashtag_id\n" +
                    "JOIN hashtags as ha ON ha.id = h.tag_id\n" +
                    "LEFT JOIN like_post as l ON l.post_id = p.id\n" +
                    "WHERE tag_name like %:query% AND is_deleted = 0 AND expiration_time >= CURDATE()\n" +
                    "GROUP BY post_hashtag_id\n" +
                    "ORDER BY post_hashtag_id DESC\n" +
                    "LIMIT 5",
            nativeQuery = true)
    List<Post> searchHashtagStories(@Param("query") String query);

    @Query(
            value = "SELECT * \n" +
                    "FROM post\n" +
                    "WHERE user_id = :userId AND expiration_time >= CURDATE() AND is_deleted = 0 AND created_time IS NOT NULL",
            nativeQuery = true)
    Optional<Post> findStoryByUserId(@Param("userId") long userId);
    @Query(
            value = "SELECT * \n" +
                    "FROM post\n" +
                    "WHERE id = :postId AND expiration_time >= CURDATE() AND is_deleted = 0 AND created_time IS NOT NULL",
            nativeQuery = true)
    Optional<Post> findStoryById(@Param("postId") long postId);
    @Modifying
    @Query(
            value = "UPDATE post " +
                    "SET is_deleted=true, caption = :now " +
                    "WHERE expiration_time<CURDATE()",
            nativeQuery = true)
    void deleteExpiredStories(@Param("now") String now);

}
