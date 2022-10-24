package com.s14ittalents.insta.hashtag;

import com.s14ittalents.insta.post.Post;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hashtag hashtag = (Hashtag) o;
        return Objects.equals(tagName, hashtag.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }
}

