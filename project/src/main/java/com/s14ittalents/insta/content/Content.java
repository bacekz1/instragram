package com.s14ittalents.insta.content;

import com.s14ittalents.insta.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "post_content")
@Getter
@Setter
@NoArgsConstructor
public class Content {
    public Content(String media, Post createdPost) {
        this.media = media;
        this.postId = createdPost;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String media;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;
}
