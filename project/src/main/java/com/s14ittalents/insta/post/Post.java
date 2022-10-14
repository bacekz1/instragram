package com.s14ittalents.insta.post;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
    @Column
    private long user_id;
    @Column
    private String caption;
    @Column
    private long location_id;
    @Column
    private boolean is_deleted;
}
