package com.s14ittalents.insta.hashtag;

import com.s14ittalents.insta.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "hashtags")
@NoArgsConstructor
@Setter
@Getter
public class Hashtag {

    public Hashtag(String tagName) {
        this.tagName = tagName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String tagName;

    @ManyToMany(mappedBy = "hashtags")
    private List<Post> postList;

}

