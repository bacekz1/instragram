package com.s14ittalents.insta.post;

import com.s14ittalents.insta.comment.Comment;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Set;

@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @Positive(message = "user_id should be positive num")
    private long user_id;
    @Column
    private String caption;
    @Column
    @Positive(message = "location_id should be positive num")
    private Long location_id;
    @Column
    private boolean is_deleted;
    @OneToMany(mappedBy = "comment")
    Set<Comment> comments;
}
