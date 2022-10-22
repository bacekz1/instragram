package com.s14ittalents.insta.comment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.util.Ownerable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment implements Ownerable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;
    @Column
    @Positive(message = "owner_id should be positive num")
    private long ownerId;
    @Column
    private String comment;
    @ManyToOne()
    @JoinColumn(name = "reply_id")
    Comment replyId;
    @OneToMany(mappedBy = "replyId")
    List<Comment> replies;
    @Column(name = "is_deleted")
    private boolean deleted;
    @ManyToMany(mappedBy = "likedComments")
    private List<User> likes;
    
    public Comment get() {
        return this;
    }

    @Override
    public long ownerId() {
        return ownerId;
    }
}
