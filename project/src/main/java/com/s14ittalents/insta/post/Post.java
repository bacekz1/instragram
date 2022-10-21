package com.s14ittalents.insta.post;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.hashtag.Hashtag;
import com.s14ittalents.insta.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String caption;
    @Column
    @Positive(message = "location_id should be positive num")
    private Long locationId;
    @Column(name = "is_deleted")
    private boolean deleted;
    @OneToMany(mappedBy = "comment")
    List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany(mappedBy = "likedPosts")
    private List<User> likes;
    @Column
    private LocalDateTime expirationTime;
    @Column
    @NotNull
    private LocalDateTime createdTime;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "hashtags_posts",
            joinColumns = { @JoinColumn(name = "post_hashtag_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private List<Hashtag> hashtags = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "person_tag",
            joinColumns = { @JoinColumn(name = "post_persontag_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_tag_id") })
        private List<User> personTags = new ArrayList<>();
}
