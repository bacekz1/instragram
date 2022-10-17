package com.s14ittalents.insta.comment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.s14ittalents.insta.post.Post;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "comments")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;
    @Column
    @Positive(message = "owner_id should be positive num")
    private long owner_id;
    @Column
    private String comment;
    @ManyToOne()
    @JoinColumn(name = "reply_id")
    Comment reply;
    @OneToMany(mappedBy = "reply")
    List<Comment> replies;
    @Column
    private boolean is_deleted;
}
