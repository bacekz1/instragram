package com.s14ittalents.insta.post;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;
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
    private Long location_id;
    @Column
    private boolean is_deleted;
    @OneToMany(mappedBy = "comment")
    List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Positive(message = "user_id should be positive num")
    private User owner;
    
    @ManyToMany(mappedBy = "likedPosts")
    private List<User> likes;
}
