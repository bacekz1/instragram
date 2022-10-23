package com.s14ittalents.insta.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.s14ittalents.insta.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "locations")
@Setter
@Getter
@NoArgsConstructor
public class Location {
    public Location(String name) {
        this.name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "locationId")
    private List<Post> posts;
}
