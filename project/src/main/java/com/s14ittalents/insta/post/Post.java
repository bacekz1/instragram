package com.s14ittalents.insta.post;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.hashtag.Hashtag;
import com.s14ittalents.insta.location.Location;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.util.Commentable;
import com.s14ittalents.insta.util.Ownerable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post implements Ownerable, Commentable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String caption;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;
    @Column(name = "is_deleted")
    private boolean deleted;
    @OneToMany(mappedBy = "post")
    List<Comment> comments;
    @OneToMany(mappedBy = "postId")
    List<Content> contents;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "hashtags_posts",
            joinColumns = {@JoinColumn(name = "post_hashtag_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Hashtag> hashtags = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_tag",
            joinColumns = {@JoinColumn(name = "post_persontag_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_tag_id")})
    private Set<User> personTags = new HashSet<>();

    @Override
    public long ownerId() {
        return owner.getId();
    }

    @Override
    public String getComment() {
        return getCaption();
    }


}
