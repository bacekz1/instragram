package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.post.Post;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@Table(name = "comments")

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @Column
    @Positive(message = "owner_id should be positive num")
    private long owner_id;
    @Column
    private String comment;
    @Column
    private Long reply_id;
    @Column
    private boolean is_deleted;
}
